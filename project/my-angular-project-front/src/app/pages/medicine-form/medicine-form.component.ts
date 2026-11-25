import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { AuthService } from '../../services/auth.service';
import { CodeItem, Medicine, MedicineSaveRequest } from '../../models/api.models';
import { calcExpiryDate, MedicineService } from '../../services/medicine.service';

/**
 * 新增/编辑 药品 POP 弹窗（两个功能共用本画面）
 * 确定：数据保存到列表；取消：关闭页面，数据废弃，列表内容不变。
 */
@Component({
  selector: 'app-medicine-form-modal',
  templateUrl: './medicine-form.component.html',
  styleUrls: ['./medicine-form.component.css']
})
export class MedicineFormComponent implements OnInit {

  @Input() medicine: Medicine | null = null;
  @Input() intakeTimeCodes: CodeItem[] = [];
  @Input() intakeFlgCodes: CodeItem[] = [];
  @Output() saved = new EventEmitter<void>();
  @Output() closed = new EventEmitter<void>();

  form: MedicineSaveRequest = this.emptyForm();
  errorMsg = '';
  submitting = false;

  constructor(private medicineService: MedicineService, private auth: AuthService) {}

  get isEdit(): boolean {
    return !!this.medicine;
  }

  /** 失效日期 = 生产日期 + 保质期（实时计算，只读展示） */
  get expiryDate(): string {
    return calcExpiryDate(this.form.productionDate, this.form.shelfLifeMonths);
  }

  ngOnInit(): void {
    if (this.medicine) {
      const m = this.medicine;
      this.form = {
        medicineName: m.medicineName,
        manufacturer: m.manufacturer,
        productionDate: m.productionDate,
        shelfLifeMonths: m.shelfLifeMonths,
        addQuantity: 0,
        tabletsPerBox: m.tabletsPerBox,
        intakeTimeCode: m.intakeTimeCode,
        intakeFlgCode: m.intakeFlgCode,
        dailyTimes: m.dailyTimes,
        tabletsPerTime: m.tabletsPerTime,
        remark: m.remark
      };
    }
  }

  onMaskClick(event: MouseEvent): void {
    // 点击遮罩不关闭，防止误操作丢失输入，仅×/取消按钮关闭
    event.stopPropagation();
  }

  submit(): void {
    this.errorMsg = '';
    const f = this.form;
    if (!f.medicineName || !f.medicineName.trim()) {
      this.errorMsg = '请输入药品名称';
    } else if (!f.productionDate) {
      this.errorMsg = '请选择生产日期';
    } else if (!f.shelfLifeMonths || f.shelfLifeMonths <= 0) {
      this.errorMsg = '请输入有效的保质期';
    } else if (f.addQuantity == null || f.addQuantity < 0) {
      if(f.intakeFlgCode && f.intakeFlgCode === '001'){
        this.errorMsg = '请输入有效的新增数量';
      }
    } else if (!f.tabletsPerBox || f.tabletsPerBox <= 0) {
      this.errorMsg = '请输入有效的单盒药品数量';
    } else if (!f.intakeTimeCode) {
      this.errorMsg = '请选择吃药时间区分';
    }  else if (!f.intakeFlgCode) {
      this.errorMsg = '请选择是否继续服用区分';
    } else if (!f.dailyTimes || f.dailyTimes <= 0) {
      this.errorMsg = '请输入有效的每日吃药次数';
    } else if (!f.tabletsPerTime || f.tabletsPerTime <= 0) {
      this.errorMsg = '请输入有效的每次吃药数量';
    } else {
      this.submitting = true;
      const payload: MedicineSaveRequest = { ...f, medicineName: f.medicineName.trim() };
      if (this.medicine) {
        debugger
        const user = this.auth.getLoginUser();
        this.medicineService.update(this.medicine.medicineId, payload, user ? user.userId : '')
          .subscribe(() => this.saved.emit());
      } else {
        debugger
        const user = this.auth.getLoginUser();
        this.medicineService.create(payload, user ? user.userId : '')
          .subscribe(() => this.saved.emit());
      }
    }
  }

  private emptyForm(): MedicineSaveRequest {
    return {
      medicineName: '',
      manufacturer: '',
      productionDate: '',
      shelfLifeMonths: null as any,
      addQuantity: 1,
      tabletsPerBox: null as any,
      intakeTimeCode: '',
      intakeFlgCode: '',
      dailyTimes: null as any,
      tabletsPerTime: null as any,
      remark: ''
    };
  }
}
