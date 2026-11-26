import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

import { AuthService } from '../../services/auth.service';
import { CodeItem, Medicine, User } from '../../models/api.models';
import { UserService } from '../../services/user.service';

/**
 * 新增/编辑 药品 POP 弹窗（两个功能共用本画面）
 * 确定：数据保存到列表；取消：关闭页面，数据废弃，列表内容不变。
 */
@Component({
  selector: 'app-user-manage-form',
  templateUrl: './user-manage-form.component.html',
  styleUrls: ['./user-manage-form.component.css']
})
export class UserManageFormComponent implements OnInit {

  @Input() user: any | null = null;
  @Output() saved = new EventEmitter<void>();
  @Output() closed = new EventEmitter<void>();

  form: User = this.emptyForm();
  errorMsg = '';
  submitting = false;

  strictEmailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
  isValidEmail(email: string): boolean {
    return this.strictEmailRegex.test(email);
  }
  constructor(private userService: UserService, private auth: AuthService) {}

  get isEdit(): boolean {
    return !!this.user;
  }

  ngOnInit(): void {
    if (this.user) {
      const m = this.user;
      this.form = {
        id: m.id,
        name: m.name,
        age: m.age,
        email: m.email,
        loginId: m.loginId,
        loginPassword: m.loginPassword
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
    if (!f.name || !f.name.trim()) {
      this.errorMsg = '请输入用户名称';
    } else if (!f.loginId || !f.loginId.trim()) {
      this.errorMsg = '请输入用户名称（登录用）';
    } else if (!f.loginPassword || !f.loginPassword.trim()) {
      this.errorMsg = '请输入密码';
    } else if (f.email !==null && f.email !=='' && !this.isValidEmail(f.email)) {
        this.errorMsg = '请输入正确的邮件地址。';
    } else {
      this.submitting = true;
      if (this.user) {
        const user = this.auth.getLoginUser();
        this.userService.update(f)
          .subscribe(() => this.saved.emit());
      } else {
        const user = this.auth.getLoginUser();
        this.userService.create(f)
          .subscribe(() => this.saved.emit());
      }
    }
    
  }

  private emptyForm(): User {
    return {
      id: '',
      name: '',
      age: 0,
      email: '',
      loginId: '',
      loginPassword: ''
    };
  }
}
