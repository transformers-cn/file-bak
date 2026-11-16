package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.LoginDTO;
import com.example.demo.entity.User;


public interface LoginService extends IService<User> {
    LoginDTO login(String loginUserId,String password);
}
