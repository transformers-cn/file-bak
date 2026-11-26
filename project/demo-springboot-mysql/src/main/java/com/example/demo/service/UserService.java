package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.dto.UserDeptDTO;
import com.example.demo.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<UserDeptDTO> listUsers(User user);
    boolean updateUserInfo(User user);
    boolean insertUserInfo(User user);
}
