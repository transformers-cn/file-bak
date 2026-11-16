package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
@TableName("t_medicine")
public class Medicine {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String user_id;             // VARCHAR(20)  NOT NULL           COMMENT '用户ID（主键1）',
    private String medicine_id;         // VARCHAR(20)  NOT NULL           COMMENT '药品ID（主键2）',
    private String medicine_name;       // VARCHAR(50)  NOT NULL           COMMENT '药品名称',
    private String manufacturer;        // VARCHAR(100)     NULL           COMMENT '生产厂家',
    private LocalDate production_date;     // DATE         NOT NULL           COMMENT '生产日期',
    private Integer shelf_life_months;   // INT          NOT NULL           COMMENT '保质期（单位：月，画面上以“年”显示）',
    private LocalDate expiry_date;         // DATE         NOT NULL           COMMENT '失效日期（=生产日期+保质期，计算生成）',
    private Integer remaining_quantity;  // INT          NOT NULL DEFAULT 0 COMMENT '新增（剩余）数量（盒），<=1 时画面提示购买',
    private Integer tablets_per_box;     // INT          NOT NULL           COMMENT '单盒药品数量（片）',
    private String intake_time_code;    // VARCHAR(3)   NOT NULL           COMMENT '吃药时间区分（码表 code_id=001 下的区分ID）',
    private Integer daily_times;         // INT          NOT NULL           COMMENT '每日吃药次数',
    private Integer tablets_per_time;    // INT          NOT NULL           COMMENT '每次吃药数量（片）',
    private String remark;              // VARCHAR(200)     NULL           COMMENT '备注注意事项',
    private Integer del_flag;            // TINYINT      NOT NULL DEFAULT 0 COMMENT '删除标志：0-正常 1-已删除',
    private String create_user;         // VARCHAR(20)      NULL           COMMENT '创建人',
    private LocalDateTime create_time;         // DATETIME         NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    private String update_user;         // VARCHAR(20)      NULL           COMMENT '更新人',
    private LocalDateTime update_time;         // DATETIME         NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
}
