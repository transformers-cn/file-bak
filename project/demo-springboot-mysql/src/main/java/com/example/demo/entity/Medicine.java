package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@TableName("t_medicine")
public class Medicine {
    @TableField("user_id")
    private String userId;             // VARCHAR(20)  NOT NULL           COMMENT '用户ID（主键1）',
    @TableId(type = IdType.AUTO)
    @TableField("medicine_id")
    private String medicineId;         // VARCHAR(20)  NOT NULL           COMMENT '药品ID（主键2）',
    @TableField("medicine_name")
    private String medicineName;       // VARCHAR(50)  NOT NULL           COMMENT '药品名称',
    @TableField("manufacturer")
    private String manufacturer;        // VARCHAR(100)     NULL           COMMENT '生产厂家',
    @TableField("production_date")
    private LocalDate productionDate;     // DATE         NOT NULL           COMMENT '生产日期',
    @TableField("shelf_life_months")
    private Integer shelfLifeMonths;   // INT          NOT NULL           COMMENT '保质期（单位：月，画面上以“年”显示）',
    @TableField("expiry_date")
    private LocalDate expiryDate;         // DATE         NOT NULL           COMMENT '失效日期（=生产日期+保质期，计算生成）',
    @TableField("remaining_quantity")
    private Integer remainingQuantity;  // INT          NOT NULL DEFAULT 0 COMMENT '新增（剩余）数量（盒），<=1 时画面提示购买',
    @TableField("tablets_per_box")
    private Integer tabletsPerBox;     // INT          NOT NULL           COMMENT '单盒药品数量（片）',
    @TableField("intake_time_code")
    private String intakeTimeCode;    // VARCHAR(3)   NOT NULL           COMMENT '吃药时间区分（码表 code_id=001 下的区分ID）',
    @TableField("intake_flg_code")
    private String intakeFlgCode;
    @TableField("daily_times")
    private Integer dailyTimes;         // INT          NOT NULL           COMMENT '每日吃药次数',
    @TableField("tablets_per_time")
    private Integer tabletsPerTime;    // INT          NOT NULL           COMMENT '每次吃药数量（片）',
    @TableField("tablets_count_all")
    private Integer tabletsCountAll;    //tablets_count_all INT NOT NULL DEFAULT 0 COMMENT '总剩余数量（片/粒）'
    @TableField("remark")
    private String remark;              // VARCHAR(200)     NULL           COMMENT '备注注意事项',
    @TableField("del_flag")
    private Integer delFlag;            // TINYINT      NOT NULL DEFAULT 0 COMMENT '删除标志：0-正常 1-已删除',
    @TableField("create_user")
    private String createUser;         // VARCHAR(20)      NULL           COMMENT '创建人',
    @TableField("create_time")
    private LocalDateTime createTime;         // DATETIME         NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    @TableField("update_user")
    private String updateUser;         // VARCHAR(20)      NULL           COMMENT '更新人',
    @TableField("update_time")
    private LocalDateTime updateTime;         // DATETIME         NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
}
