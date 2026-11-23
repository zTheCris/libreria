import Swal  from 'sweetalert2';
import { LoginService } from './../login-service';
import { JwtToken } from './../jwt-token';
import { Component, inject, OnInit } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { timer } from 'rxjs';
@Component({
  selector: 'app-login-component',
  imports: [ReactiveFormsModule],
  templateUrl: './login-component.html',
  styleUrl: './login-component.css',
})
export class LoginComponent implements OnInit{
  loginService = inject(LoginService);
  router = inject(Router);

  profileForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });
  // Chiama il service login
  login() {
    this.loginService.login(this.profileForm.value.email!,this.profileForm.value.password!).subscribe({
      next: (res: JwtToken) =>{
        // Lo setto nel service
        this.loginService.salvaToken(res);
        timer(1500).subscribe(()=>{
          this.router.navigate(['/']);
        })
      },
      error: (err: HttpErrorResponse) =>{
        Swal.fire({title: "Errore. Probabili credenziali sbagliate",icon:'error'})
      }
    })

  }
  // Uso onInit cosi eseguo il body SOLO alla fine del caricamento
  // del DOM.
  ngOnInit(){
    if(!this.loginService.isTokenExpired()){
      Swal.fire({title:"Sei già autenticato!",icon:'info',})
    }
    // if(this.loginService.getToken()!=null && this.loginService.isTokenExpired()){
    //   Swal.fire({title:"Autenticazione scaduta.",icon:'error'})
    // }
  }
}
