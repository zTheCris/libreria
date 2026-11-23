import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { LoginService } from './login-service';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
/**
 * L'interceptor intercetta tutte le richieste HTTP effettuate da un componente, e se il token è presente in localStorage, lo aggiunge all'header della richiesta.
 * Se il token non è presente, non fa nulla.
 * Se la risposta è 401, significa che il token è scaduto, quindi lo rimuovo da localStorage e rimando l'utente alla pagina di login.
 * @param req
 * @param next
 * @returns
 */
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
    // console.log(req.headers)
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
