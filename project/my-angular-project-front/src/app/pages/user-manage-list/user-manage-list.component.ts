import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import {  User } from '../../models/api.models';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
@Component({
  selector: 'app-user-manage-list',
  templateUrl: './user-manage-list.component.html',
  styleUrls: ['./user-manage-list.component.css']
})
export class UserListComponent implements OnInit {

  userId = '';
  userName = '';
  searchKeyword = '';
  userList: any;
  loading = false;

  errorMsg = '';

  modalVisible = false;
  editingUser: any | null = null;

  /** 剩余数量阈值：小于等于该值时提示购买 */
  private readonly LOW_STOCK_THRESHOLD = 1;
  /** 失效日期距今不足该天数时高亮提醒 */
  private readonly EXPIRY_WARNING_DAYS = 90;

  constructor(
    private userService: UserService,
    private auth: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.errorMsg = '';
    const user = this.auth.getLoginUser();
    this.userId = user ? user.userId : '';
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }
    this.userName = user.userName;
    
    this.search();
  }

  /** 检索 */
  search(): void {
    this.loading = true;
    const user = this.auth.getLoginUser();
    
    this.userService.search(this.searchKeyword.trim()).subscribe(result => {
      this.userList = result;
      this.loading = false;
    });
  }

  /** 重置检索条件并重新查询 */
  reset(): void {
    this.errorMsg = '';
    this.searchKeyword = '';
    this.search();
  }

  /** 打开新增 POP */
  openCreate(): void {
    this.errorMsg = '';
    this.editingUser = null;
    this.modalVisible = true;
  }

  /** 打开编辑 POP */
  openEdit(item: User): void {
    this.errorMsg = '';
    this.editingUser = item;
    this.modalVisible = true;
  }

  closeModal(): void {
    this.errorMsg = '';
    this.modalVisible = false;
    this.editingUser = null;
  }

  onSaved(): void {
    this.errorMsg = '';
    this.closeModal();
    this.search();
  }

  /** 删除：先提示确认，再从列表删除 */
  confirmDelete(item: User): void {
    this.errorMsg = '';
    if (window.confirm(`确定要删除用户「${item.name}」吗？`)) {
      const user = this.auth.getLoginUser();
      this.userService.deleteUser(Number(item.id));
      setTimeout(() => {
        this.search();
      }, 1000);
    }
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  openMedicinePage(): void {
      this.router.navigate(['/medicines']);
      return;
  }
}
