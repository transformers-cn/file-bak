import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import {
  ApiResponse,
  UserListResult,
  User
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class UserService {

  // private readonly baseUrl = `${environment.apiBaseUrl}/users`;
  private readonly baseUrl = `${environment.apiBaseUrl}/users/searchByName`;
  private readonly saveUrl = `${environment.apiBaseUrl}/users/insert-user-info`;
  private readonly updateUrl = `${environment.apiBaseUrl}/users/update-user-info`;
  private readonly deleteUrl = `${environment.apiBaseUrl}/users/delete-user-record`;

  constructor(private http: HttpClient) {}

  /** 用户检索：按用户名称模糊查询 */
  search(userName: string): Observable<UserListResult> {  
    return this.http
      .get<ApiResponse<UserListResult>>(this.baseUrl, { params: { name:userName } })
      .pipe(map(res => res.data));
  }

  /** 新增用户 */
  create(req: User): Observable<User> {
    const user: User = {
      id: '',
      name: req.name,
      age: req.age,
      email: req.email,
      loginId: req.loginId,
      loginPassword: req.loginPassword
    };

    return this.http
      .post<ApiResponse<User>>(this.saveUrl, { ...user })
      .pipe(map(res => res.data));
  }

  /** 编辑用户 */
  update(req: User): Observable<User> {
    const updated: User = {
      id: req.id,
      name: req.name,
      age: req.age,
      email: req.email,
      loginId: req.loginId,
      loginPassword: req.loginPassword
      };
    return this.http
      .post<ApiResponse<User>>(this.updateUrl, { ...updated })
      .pipe(map(res => res.data));
  }
  deleteUser(id: number) {
    const url = `${environment.apiBaseUrl}/users/${id}`;
    this.http.delete(url).subscribe({
      next: () => {
        console.log('删除成功');
      },
      error: (err) => {
        console.error('删除失败', err);
      }
    });
  }
  /** 删除用户 */
  remove(userId: string): Observable<boolean> {
    const removeUser: any = {
      userId: userId
    };
    return this.http
      .post<ApiResponse<boolean>>(this.deleteUrl, { ...removeUser })
      .pipe(map(res => res.data));
  }
}
