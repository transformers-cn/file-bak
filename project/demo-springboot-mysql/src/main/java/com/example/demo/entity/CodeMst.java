package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("t_code_mst")
public class CodeMst {
    @TableField("code_type_id")
    private String codeTypeId;
    @TableField("code_id")
    private String codeId;
    @TableField("display_order")
    private String displayOrder;
    @TableField("code_name")
    private String codeName;
    @TableField("del_flag")
    private Integer delFlag;
}
