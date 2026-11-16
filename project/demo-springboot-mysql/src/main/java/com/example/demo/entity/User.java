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

    private String login_id;//登录用户名

    private String login_password;//登录password,建议存储加密后的密文

    private String name;

    private Integer age;

    private String email;

    private String create_user;//创建人

    private LocalDateTime createTime;

    private String update_user;//更新人

    private LocalDateTime updateTime;

    private Integer del_flag;//'删除标志：0-正常 1-已删除'
}
