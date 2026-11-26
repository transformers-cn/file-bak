package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.dto.UserDeptDTO;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 列表查询
    public List<UserDeptDTO> listUsers(User user) {
        return userMapper.selectUserDeptList(user.getName(), user.getAge());
    }

    public boolean updateUserInfo(User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(user != null && user.getId() != null, User::getId, user.getId());
        User userForUpdate = userMapper.selectOne(queryWrapper);

        userForUpdate.setName(user.getName());
        userForUpdate.setAge(user.getAge());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setLoginId(user.getLoginId());
        userForUpdate.setLoginPassword(user.getLoginPassword());

        userForUpdate.setUpdateTime(LocalDateTime.now());
        userForUpdate.setUpdateUser("admin");

        return updateById(userForUpdate);
    }

    public boolean insertUserInfo(User user){
        User userForUpdate = new User();

        userForUpdate.setId(makeUserIdForInsertData());
        userForUpdate.setName(user.getName());
        userForUpdate.setAge(user.getAge());
        userForUpdate.setEmail(user.getEmail());
        userForUpdate.setLoginId(user.getLoginId());
        userForUpdate.setLoginPassword(user.getLoginPassword());

        userForUpdate.setCreateTime(LocalDateTime.now());
        userForUpdate.setCreateUser("admin");
        userForUpdate.setUpdateTime(LocalDateTime.now());
        userForUpdate.setUpdateUser("admin");

        userForUpdate.setDelFlag(0);

        return save(userForUpdate);
    }

    public Long makeUserIdForInsertData(){
        String strMaxMedicineId = userMapper.getMaxUserId();
        int intMaxMedicineId= Integer.parseInt(strMaxMedicineId)+1;
        return Long.valueOf(intMaxMedicineId);
    }
}
