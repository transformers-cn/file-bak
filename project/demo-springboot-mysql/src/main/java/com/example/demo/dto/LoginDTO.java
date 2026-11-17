package com.example.demo.dto;
import lombok.Data;

@Data
public class LoginDTO {
    private String userId;// string, 用户ID（药品表主键1）
    private String userName;// string, 用户姓名（列表页欢迎语"张 先生/女士"使用）
    private String token;// string, 登录令牌（前端存 sessionStorage）
}
