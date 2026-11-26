import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username = '';
  password = '';
  errorMsg = '';
  loading = false;

  constructor(private auth: AuthService, private router: Router) {}

  login(): void {
    this.errorMsg = '';
    if (!this.username || !this.password) {
      this.errorMsg = '请输入用户名和密码';
      return;
    }
    this.loading = true;
    this.auth.login({ username: this.username, password: this.password }).subscribe(result => {
      this.loading = false;
      if (result) {
        this.auth.saveSession(result);
        if(this.username==='admin'){
          this.router.navigate(['/user']);
        }else{
          this.router.navigate(['/medicines']);
        }
      } else {
        this.errorMsg = '用户名或密码错误';
      }
    });
  }
}
