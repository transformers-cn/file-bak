import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CodeItem, Medicine } from '../../models/api.models';
import { AuthService } from '../../services/auth.service';
import { MedicineService } from '../../services/medicine.service';

@Component({
  selector: 'app-medicine-list',
  templateUrl: './medicine-list.component.html',
  styleUrls: ['./medicine-list.component.css']
})
export class MedicineListComponent implements OnInit {

  userId = '';
  userName = '';
  searchKeyword = '';
  list: Medicine[] = [];
  intakeTimeCodes: CodeItem[] = [];
  loading = false;

  modalVisible = false;
  editingMedicine: Medicine | null = null;

  /** 剩余数量阈值：小于等于该值时提示购买 */
  private readonly LOW_STOCK_THRESHOLD = 1;
  /** 失效日期距今不足该天数时高亮提醒 */
  private readonly EXPIRY_WARNING_DAYS = 90;

  constructor(
    private medicineService: MedicineService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const user = this.auth.getLoginUser();
    this.userId = user ? user.userId : '';
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }
    this.userName = user.userName;
    this.medicineService.getIntakeTimeCodes().subscribe(codes => {
      this.intakeTimeCodes = codes;
    });
    this.search();
  }

  /** 检索 */
  search(): void {
    this.loading = true;
    const user = this.auth.getLoginUser();
    this.medicineService.search(this.userId,this.searchKeyword.trim()).subscribe(result => {
      this.list = result.list;
      this.loading = false;
    });
  }

  /** 重置检索条件并重新查询 */
  reset(): void {
    this.searchKeyword = '';
    this.search();
  }

  /** 打开新增 POP */
  openCreate(): void {
    this.editingMedicine = null;
    this.modalVisible = true;
  }

  /** 打开编辑 POP */
  openEdit(item: Medicine): void {
    this.editingMedicine = item;
    this.modalVisible = true;
  }

  closeModal(): void {
    this.modalVisible = false;
    this.editingMedicine = null;
  }

  onSaved(): void {
    this.closeModal();
    this.search();
  }

  /** 删除：先提示确认，再从列表删除 */
  confirmDelete(item: Medicine): void {
    if (window.confirm(`确定要删除药品「${item.medicineName}」吗？`)) {
      const user = this.auth.getLoginUser();
      this.medicineService.remove(item.medicineId, user ? user.userId : '').subscribe(() => this.search());
    }
  }

  /** 低库存提示列表 */
  get lowStockList(): string[] {
    return this.list
      .filter(m => m.remainingQuantity <= this.LOW_STOCK_THRESHOLD)
      .map(m => m.medicineName);
  }

  intakeTimeName(code: string): string {
    return MedicineService.codeName(this.intakeTimeCodes, code);
  }

  /** 失效日期距今不足阈值天数 */
  isExpiringSoon(expiryDate: string): boolean {
    if (!expiryDate) {
      return false;
    }
    const diff = new Date(expiryDate).getTime() - Date.now();
    return diff >= 0 && diff <= this.EXPIRY_WARNING_DAYS * 24 * 3600 * 1000;
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
