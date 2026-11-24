/** 吃药时间区分（码表项） */
export interface CodeItem {
  /** 区分ID，如 '001' */
  codeId: string;
  /** 表示顺序 */
  displayOrder: number;
  /** 区分名称，如 餐前/随餐/餐后 */
  codeName: string;
}

/** 药品信息（对应 DB 表 medicine） */
export interface Medicine {
  /** 用户ID（主键1） */
  userId: string;
  /** 药品ID（主键2） */
  medicineId: string;
  /** 药品名称 */
  medicineName: string;
  /** 生产厂家 */
  manufacturer: string;
  /** 生产日期 yyyy-MM-dd */
  productionDate: string;
  /** 保质期（数值，单位：月） */
  shelfLifeMonths: number;
  /** 失效日期 yyyy-MM-dd（由生产日期+保质期计算，只读展示） */
  expiryDate: string;
  /** 剩余数量（盒） */
  remainingQuantity: number;
  /** 单盒药品数量（片） */
  tabletsPerBox: number;
  /** 吃药时间区分（码表 001 的区分ID：001-餐前 002-随餐 003-餐后） */
  intakeTimeCode: string;
  /** 每日吃药次数 */
  dailyTimes: number;
  /** 总数量（片） */
  tabletsCountAll: number;
    /** 每次吃药数量（片） */
  tabletsPerTime: number;
  /** 备注注意事项 */
  remark: string;
}

/** 新增/编辑 药品请求体 */
export interface MedicineSaveRequest {
  medicineName: string;
  manufacturer: string;
  productionDate: string;
  shelfLifeMonths: number;
  /** 新增数量（盒），编辑时为增量累加 */
  addQuantity: number;
  tabletsPerBox: number;
  intakeTimeCode: string;
  dailyTimes: number;
  tabletsPerTime: number;
  remark: string;
}

/** 登录请求体 */
export interface LoginRequest {
  username: string;
  password: string;
}

/** 登录响应数据 */
export interface LoginResult {
  userId: string;
  /** 用户姓名，用于列表页欢迎语 */
  userName: string;
  token: string;
}

/** 统一 API 响应包装 */
export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}

/** 药品列表响应数据 */
export interface MedicineListResult {
  total: number;
  list: Medicine[];
}
