import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map, delay } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { ApiResponse, LoginRequest, LoginResult } from '../models/api.models';
import { MockDb } from './mock-db';

/**
 * 登录相关 API（虚拟 URL：POST {apiBaseUrl}/auth/login）
 * useMock=true 时返回本地 Mock 数据，便于前端独立联调。
 */
@Injectable({ providedIn: 'root' })
export class AuthService {

  private readonly loginUrl = `${environment.apiBaseUrl}/auth/login`;

  constructor(private http: HttpClient) {}

  login(req: LoginRequest): Observable<LoginResult> {
    if (environment.useMock) {
      // 虚拟逻辑：admin/123456 校验通过，其他用户名密码返回失败
      if (req.username !== 'admin' || req.password !== '123456') {
        return of(null as any).pipe(delay(300));
      }
      const result: LoginResult = {
        userId: 'U0001',
        userName: '张',
        token: 'mock-token-' + Date.now()
      };
      return of(result).pipe(delay(300));
    }
    return this.http.post<ApiResponse<LoginResult>>(this.loginUrl, req).pipe(
      map(res => res.data)
    );
  }

  saveSession(result: LoginResult): void {
    sessionStorage.setItem('loginUser', JSON.stringify(result));
  }

  getLoginUser(): LoginResult | null {
    const raw = sessionStorage.getItem('loginUser');
    return raw ? (JSON.parse(raw) as LoginResult) : null;
  }

  logout(): void {
    sessionStorage.removeItem('loginUser');
  }
}
