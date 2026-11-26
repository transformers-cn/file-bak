package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String loginId;//登录用户名

    private String loginPassword;//登录password,建议存储加密后的密文

    private String name;

    private Integer age;

    private String email;

    private String createUser;//创建人

    private LocalDateTime createTime;

    private String updateUser;//更新人

    private LocalDateTime updateTime;

    private Integer delFlag;//'删除标志：0-正常 1-已删除'
}
