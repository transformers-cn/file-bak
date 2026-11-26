import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { UserListComponent } from './pages/user-manage-list/user-manage-list.component';
import { UserManageFormComponent } from './pages/user-manage-form/user-manage-form.component';
import { MedicineListComponent } from './pages/medicine-list/medicine-list.component';
import { MedicineFormComponent } from './pages/medicine-form/medicine-form.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'medicines', component: MedicineListComponent },
  { path: 'user', component: UserListComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UserListComponent,
    UserManageFormComponent,
    MedicineListComponent,
    MedicineFormComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
