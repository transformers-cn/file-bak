import { Medicine, CodeItem } from '../models/api.models';

/**
 * 本地 Mock 数据（内存存储），仅在 environment.useMock=true 时使用。
 * 后续接入真实后端后可整体删除本文件。
 */
export class MockDb {

  /** 吃药时间区分码表 */
  static readonly intakeTimeCodes: CodeItem[] = [
    { codeId: '001', displayOrder: 1, codeName: '餐前' },
    { codeId: '002', displayOrder: 2, codeName: '随餐' },
    { codeId: '003', displayOrder: 3, codeName: '餐后' }
  ];

  /** 药品表初始数据（与需求文档示例一致） */
  static medicines: Medicine[] = [
    {
      userId: 'U0001',
      medicineId: 'M0001',
      medicineName: '阿司匹林',
      manufacturer: 'XXX制药厂',
      productionDate: '2026-01-01',
      shelfLifeMonths: 24,
      expiryDate: '2028-01-01',
      remainingQuantity: 3,
      tabletsPerBox: 7,
      intakeTimeCode: '002',
      dailyTimes: 1,
      tabletsPerTime: 1,
      remark: ''
    },
    {
      userId: 'U0001',
      medicineId: 'M0002',
      medicineName: '头孢',
      manufacturer: 'OOO制药厂',
      productionDate: '2026-04-30',
      shelfLifeMonths: 36,
      expiryDate: '2029-04-30',
      remainingQuantity: 1,
      tabletsPerBox: 10,
      intakeTimeCode: '003',
      dailyTimes: 3,
      tabletsPerTime: 2,
      remark: ''
    }
  ];

  private static seq = 2;

  static nextMedicineId(): string {
    MockDb.seq += 1;
    return 'M' + ('0000' + MockDb.seq).slice(-4);
  }
}
