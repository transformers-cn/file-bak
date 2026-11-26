import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { map, delay } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import {
  ApiResponse,
  UserListResult,
  User,
  CodeItem,
  Medicine,
  MedicineListResult,
  MedicineSaveRequest
} from '../models/api.models';

@Injectable({ providedIn: 'root' })
export class UserService {

  // private readonly baseUrl = `${environment.apiBaseUrl}/users`;
  private readonly baseUrl = `${environment.apiBaseUrl}/users/searchByName`;
  private readonly saveUrl = `${environment.apiBaseUrl}/users/insert-user-info`;
  private readonly updateUrl = `${environment.apiBaseUrl}/users/update-user-info`;
  private readonly deleteUrl = `${environment.apiBaseUrl}/users/delete-user-record`;




  private readonly codeUrl = `${environment.apiBaseUrl}/common/codes/intake-time`;
  private readonly codeSearchByIdUrl = `${environment.apiBaseUrl}/common/codes/search-by-Id`;

  private readonly updateMedicalRecordForPerdayUrl = `${environment.apiBaseUrl}/medicalRecord/update-medical-record-for-perday`;



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
        // return true;
        // // 删除成功后刷新列表
        // this.search('');
        // console.log('刷新列表');
      },
      error: (err) => {
        console.error('删除失败', err);
        // return false;
      }
    });
    // this.http.delete(url).subscribe(() => {
    //     this.user = this.user.filter(t => t.id !== id);
    // });
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

  // /** 获取吃药时间区分码表 */
  // getIntakeTimeCodes(): Observable<CodeItem[]> {
  //   // if (environment.useMock) {
  //   //   return of(MockDb.intakeTimeCodes.map(c => ({ ...c }))).pipe(delay(100));
  //   // }
  //   // return this.http
  //   //   .get<ApiResponse<CodeItem[]>>(this.codeUrl)
  //   //   .pipe(map(res => res.data));

  //   return this.http
  //     .get<ApiResponse<CodeItem[]>>(this.codeSearchByIdUrl , { params: { codeTypeId:'001' } })
  //     .pipe(map(res => res.data));
  // }
  // getIntakeFlgCodes(): Observable<CodeItem[]> { 
  //   return this.http
  //     .get<ApiResponse<CodeItem[]>>(this.codeSearchByIdUrl , { params: { codeTypeId:'002' } })
  //     .pipe(map(res => res.data));
  // }

  // /** 根据区分ID获取区分名称 */
  // static codeName(codes: CodeItem[], codeId: string): string {
  //   const found = codes.find(c => c.codeId === codeId);
  //   return found ? found.codeName : codeId;
  // }

  //   /** 更新每日服用药品数量 */
  // updateMedicalRecordForPerday(userId: string): Observable<ApiResponse<boolean>> {
  //   const updateMedicalRecordForPerday: any = {
  //     userId: userId
  //   };
  //   return this.http
  //     .post<ApiResponse<boolean>>(this.updateMedicalRecordForPerdayUrl, { ...updateMedicalRecordForPerday });
  // }
}
