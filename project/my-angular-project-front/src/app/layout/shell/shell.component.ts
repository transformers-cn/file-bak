import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-shell',
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.css']
})
export class ShellComponent {

  userName = '';
  showUserMenu = false;

  constructor(private auth: AuthService, private router: Router) {
    const user = this.auth.getLoginUser();
    if (user) {
      this.userName = user.userName;
      this.showUserMenu = user.userId === '1';
    }
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
