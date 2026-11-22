import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginService } from './login-service';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  // Poichè mi serve il token in localStorage, lo vado a vedere dal Service.
  const loginService = inject(LoginService);
  const router = inject(Router);
  // Se il token è presente
  if (loginService.getToken()) {
    // Dato che le richieste HTTP sono immutabili, dobbiamo crearne una nuova con Header impostato.
    const nuovaRichiesta = req.clone({
      setHeaders: {
        Authorization: `Bearer ${loginService.getToken()}`
      }
    });
    console.log(req.headers)
    return next(nuovaRichiesta);
  }

  return next(req).pipe(
     catchError((err) => {
      if (err.status === 401) {
        loginService.logout();
        router.navigate(['/login']);
      }

      return throwError(() => err);
    }
  ));
};
