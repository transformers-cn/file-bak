package com.example.demo.dto;
import lombok.Data;

@Data
public class UserDeptDTO {
    private Long id;
    private String name;
    private Integer age;
    private Long deptId;
    private String deptName;  // 来自 dept 表
}
