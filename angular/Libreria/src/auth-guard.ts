import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from './login-service';
import { inject } from '@angular/core';

 // Dichiaro una variabile authGuard, e il contenuto della arrow function sasrà il valore.

export const authGuard: CanActivateFn = () => {
  // Devo controllare, chiamando loginService, se il token è presente in localStorage o meno.
  const loginService = inject(LoginService);
  // Se non è presente, lo rimando alla pagina di login, quindi mi serve Router
  const router = inject(Router);

  if(loginService.getToken()!= null)
    return true;
  // Se è null, lo rimando
  return router.parseUrl("/login");
};
