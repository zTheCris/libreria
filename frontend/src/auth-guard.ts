import { CanActivateFn, Router } from '@angular/router';
import { LoginService } from './login-service';
import { inject } from '@angular/core';
import Swal from 'sweetalert2';

 // Dichiaro una variabile authGuard, e il contenuto della arrow function sasrà il valore.

export const authGuard: CanActivateFn = () => {
  // Devo controllare, chiamando loginService, se il token è presente in localStorage o meno.
  const loginService = inject(LoginService);
  // Se non è presente, lo rimando alla pagina di login, quindi mi serve Router
  const router = inject(Router);

  // Controllo se il token è scaduto.


  // Se il token NON è scaduto
  if(!loginService.isTokenExpired()){
    return true
  }
  return router.parseUrl("/login");
   // if (loginService.isTokenExpired()){
  //      localStorage.removeItem('token');
  //   return router.parseUrl("/login");
  // }

  // Se è null, lo rimando
};
