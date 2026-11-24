import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { map, delay } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import {
  ApiResponse,
  CodeItem,
  Medicine,
  MedicineListResult,
  MedicineSaveRequest
} from '../models/api.models';
import { MockDb } from './mock-db';

/**
 * 失效日期计算：生产日期 + 保质期（月）
 * 如 2026-01-01 + 24个月 = 2028-01-01
 */
export function calcExpiryDate(productionDate: string, shelfLifeMonths: number): string {
  if (!productionDate || shelfLifeMonths == null) {
    return '';
  }
  if(typeof shelfLifeMonths === 'string'){
    shelfLifeMonths = parseInt(shelfLifeMonths, 10);
  }
  const [y, m, d] = productionDate.split('-').map(Number);
  const date = new Date(y, m - 1 + shelfLifeMonths, d);
  const mm = ('0' + (date.getMonth() + 1)).slice(-2);
  const dd = ('0' + date.getDate()).slice(-2);
  return `${date.getFullYear()}-${mm}-${dd}`;
}

/**
 * 药品相关 API（均为虚拟 URL，后续替换 environment.apiBaseUrl 即可对接真实后端）
 *
 *  GET    {apiBaseUrl}/medicines?medicineName=xxx   药品检索（列表）
 *  POST   {apiBaseUrl}/medicines                    新增药品
 *  PUT    {apiBaseUrl}/medicines/{medicineId}       编辑药品
 *  DELETE {apiBaseUrl}/medicines/{medicineId}       删除药品
 *  GET    {apiBaseUrl}/codes/intake-time            吃药时间区分码表
 */
@Injectable({ providedIn: 'root' })
export class MedicineService {

  private readonly baseUrl = `${environment.apiBaseUrl}/medicalRecord/search-medical-record`;
  private readonly codeUrl = `${environment.apiBaseUrl}/common/codes/intake-time`;
  private readonly saveUrl = `${environment.apiBaseUrl}/medicalRecord/save-medical-record`;
  private readonly updateUrl = `${environment.apiBaseUrl}/medicalRecord/update-medical-record`;
  private readonly deleteUrl = `${environment.apiBaseUrl}/medicalRecord/delete-medical-record`;
  private readonly updateMedicalRecordForPerdayUrl = `${environment.apiBaseUrl}/medicalRecord/update-medical-record-for-perday`;

  constructor(private http: HttpClient) {}

  /** 药品检索：按药品名称模糊查询 */
  search(userId: string,medicineName: string): Observable<MedicineListResult> {
    if (environment.useMock) {
      const list = MockDb.medicines.filter(
        m => !medicineName || m.medicineName.indexOf(medicineName) !== -1
      );
      const result: MedicineListResult = { total: list.length, list: list.map(m => ({ ...m })) };
      return of(result).pipe(delay(200));
    }
    
    return this.http
      .get<ApiResponse<MedicineListResult>>(this.baseUrl, { params: { userId:userId,medicineName: medicineName || '' } })
      .pipe(map(res => res.data));
  }

  /** 新增药品 */
  create(req: MedicineSaveRequest, userId: string): Observable<Medicine> {
    if (environment.useMock) {
      const medicine: Medicine = {
        userId,
        medicineId: MockDb.nextMedicineId(),
        medicineName: req.medicineName,
        manufacturer: req.manufacturer,
        productionDate: req.productionDate,
        shelfLifeMonths: req.shelfLifeMonths,
        expiryDate: calcExpiryDate(req.productionDate, req.shelfLifeMonths),
        remainingQuantity: req.addQuantity,
        tabletsPerBox: req.tabletsPerBox,
        intakeTimeCode: req.intakeTimeCode,
        dailyTimes: req.dailyTimes,
        tabletsPerTime: req.tabletsPerTime,
        remark: req.remark
      };
      MockDb.medicines.push(medicine);
      return of({ ...medicine }).pipe(delay(200));
    }

    const medicine: Medicine = {
      userId,
      medicineId: MockDb.nextMedicineId(),
      medicineName: req.medicineName,
      manufacturer: req.manufacturer,
      productionDate: req.productionDate,
      shelfLifeMonths: req.shelfLifeMonths,
      expiryDate: calcExpiryDate(req.productionDate, req.shelfLifeMonths),
      remainingQuantity: req.addQuantity,
      tabletsPerBox: req.tabletsPerBox,
      intakeTimeCode: req.intakeTimeCode,
      dailyTimes: req.dailyTimes,
      tabletsPerTime: req.tabletsPerTime,
      remark: req.remark
    };

    return this.http
      .post<ApiResponse<Medicine>>(this.saveUrl, { ...medicine, userId })
      .pipe(map(res => res.data));
  }

  /** 编辑药品（新增数量按累加处理） */
  update(medicineId: string, req: MedicineSaveRequest, userId: string): Observable<Medicine> {
    // if (environment.useMock) {
    //   const idx = MockDb.medicines.findIndex(m => m.medicineId === medicineId);
    //   if (idx === -1) {
    //     return throwError('药品不存在');
    //   }
    //   const old = MockDb.medicines[idx];
    //   const updated: Medicine = {
    //     ...old,
    //     userId: userId,
    //     medicineId: medicineId,
    //     medicineName: req.medicineName,
    //     manufacturer: req.manufacturer,
    //     productionDate: req.productionDate,
    //     shelfLifeMonths: req.shelfLifeMonths,
    //     expiryDate: calcExpiryDate(req.productionDate, req.shelfLifeMonths),
    //     remainingQuantity: old.remainingQuantity + req.addQuantity,
    //     tabletsPerBox: req.tabletsPerBox,
    //     intakeTimeCode: req.intakeTimeCode,
    //     dailyTimes: req.dailyTimes,
    //     tabletsPerTime: req.tabletsPerTime,
    //     remark: req.remark
    //   };
    //   MockDb.medicines[idx] = updated;
    //   return of({ ...updated }).pipe(delay(200));
    // }
    // return this.http
    //   .put<ApiResponse<Medicine>>(`${this.baseUrl}/${medicineId}`, req)
    //   .pipe(map(res => res.data));
    debugger
    const idx = MockDb.medicines.findIndex(m => m.medicineId === medicineId);
    if (idx === -1) {
      return throwError('药品不存在');
    }
    const old = MockDb.medicines[idx];
    const updated: Medicine = {
        ...old,
        userId: userId,
        medicineId: medicineId,
        medicineName: req.medicineName,
        manufacturer: req.manufacturer,
        productionDate: req.productionDate,
        shelfLifeMonths: req.shelfLifeMonths,
        expiryDate: calcExpiryDate(req.productionDate, req.shelfLifeMonths),
        remainingQuantity: old.remainingQuantity + req.addQuantity,
        tabletsPerBox: req.tabletsPerBox,
        intakeTimeCode: req.intakeTimeCode,
        dailyTimes: req.dailyTimes,
        tabletsPerTime: req.tabletsPerTime,
        remark: req.remark
      };
    return this.http
      .post<ApiResponse<Medicine>>(this.updateUrl, { ...updated })
      .pipe(map(res => res.data));
  }

  /** 删除药品 */
  remove(medicineId: string,userId: string): Observable<boolean> {
    if (environment.useMock) {
      const idx = MockDb.medicines.findIndex(m => m.medicineId === medicineId);
      if (idx !== -1) {
        MockDb.medicines.splice(idx, 1);
      }
      return of(idx !== -1).pipe(delay(200));
    }
    const removeMedicine: any = {
      userId: userId,
      medicineId: medicineId
    };
    return this.http
      .post<ApiResponse<boolean>>(this.deleteUrl, { ...removeMedicine })
      .pipe(map(res => res.data));
  }

  /** 获取吃药时间区分码表 */
  getIntakeTimeCodes(): Observable<CodeItem[]> {
    if (environment.useMock) {
      return of(MockDb.intakeTimeCodes.map(c => ({ ...c }))).pipe(delay(100));
    }
    return this.http
      .get<ApiResponse<CodeItem[]>>(this.codeUrl)
      .pipe(map(res => res.data));
  }

  /** 根据区分ID获取区分名称 */
  static codeName(codes: CodeItem[], codeId: string): string {
    const found = codes.find(c => c.codeId === codeId);
    return found ? found.codeName : codeId;
  }

    /** 更新每日服用药品数量 */
  updateMedicalRecordForPerday(userId: string): Observable<boolean> {
    const updateMedicalRecordForPerday: any = {
      userId: userId
    };
    return this.http
      .post<ApiResponse<boolean>>(this.updateMedicalRecordForPerdayUrl, { ...updateMedicalRecordForPerday })
      .pipe(map(res => res.data));
  }
}
