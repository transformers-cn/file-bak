package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import com.example.demo.dto.UserDeptDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 继承 BaseMapper 后即拥有单表 CRUD 能力，无需写 XML。
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<UserDeptDTO> selectUserDeptList(
            @Param("name") String name,
            @Param("age") Integer age
    );
}
