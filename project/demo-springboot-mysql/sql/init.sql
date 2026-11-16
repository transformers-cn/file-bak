-- 初始化脚本：在本地 MySQL 中执行
CREATE DATABASE IF NOT EXISTS demo_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE demo_db;

CREATE TABLE IF NOT EXISTS t_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(64)  DEFAULT NULL COMMENT '姓名',
    age         INT          DEFAULT NULL COMMENT '年龄',
    email       VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT '用户表';

INSERT INTO t_user (name, age, email) VALUES
('张三', 20, 'zhangsan@example.com'),
('李四', 25, 'lisi@example.com'),
('王五', 30, 'wangwu@example.com');


CREATE TABLE IF NOT EXISTS t_dept (
    dept_id BIGINT PRIMARY KEY,
    dept_name VARCHAR(50)
);

ALTER TABLE t_user
ADD COLUMN dept_id INT NOT NULL DEFAULT 0 COMMENT '部门ID';

ALTER TABLE t_user ADD COLUMN login_id VARCHAR(50) DEFAULT NULL COMMENT '登录用户名';
ALTER TABLE t_user ADD COLUMN login_password VARCHAR(100) DEFAULT NULL COMMENT '建议存储加密后的密文';
ALTER TABLE t_user ADD COLUMN del_flag INT NOT NULL DEFAULT 0 COMMENT '删除标志：0-正常 1-已删除';
ALTER TABLE t_user ADD COLUMN create_user VARCHAR(50) DEFAULT NULL COMMENT '创建人';
ALTER TABLE t_user ADD COLUMN update_user VARCHAR(50) DEFAULT NULL COMMENT '更新人';


-- ----------------------------------------------------------------------------
-- 2. 药品表 medicine
--    主键：user_id + medicine_id（对应设计文档中的 主键1 / 主键2）
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS t_medicine;
CREATE TABLE t_medicine (
  user_id             VARCHAR(20)  NOT NULL           COMMENT '用户ID（主键1）',
  medicine_id         VARCHAR(20)  NOT NULL           COMMENT '药品ID（主键2）',
  medicine_name       VARCHAR(50)  NOT NULL           COMMENT '药品名称',
  manufacturer        VARCHAR(100)     NULL           COMMENT '生产厂家',
  production_date     DATE         NOT NULL           COMMENT '生产日期',
  shelf_life_months   INT          NOT NULL           COMMENT '保质期（单位：月，画面上以“年”显示）',
  expiry_date         DATE         NOT NULL           COMMENT '失效日期（=生产日期+保质期，计算生成）',
  remaining_quantity  INT          NOT NULL DEFAULT 0 COMMENT '新增（剩余）数量（盒），<=1 时画面提示购买',
  tablets_per_box     INT          NOT NULL           COMMENT '单盒药品数量（片）',
  intake_time_code    VARCHAR(3)   NOT NULL           COMMENT '吃药时间区分（码表 code_id=001 下的区分ID）',
  daily_times         INT          NOT NULL           COMMENT '每日吃药次数',
  tablets_per_time    INT          NOT NULL           COMMENT '每次吃药数量（片）',
  remark              VARCHAR(200)     NULL           COMMENT '备注注意事项',
  del_flag            TINYINT      NOT NULL DEFAULT 0 COMMENT '删除标志：0-正常 1-已删除',
  create_user         VARCHAR(20)      NULL           COMMENT '创建人',
  create_time         DATETIME         NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_user         VARCHAR(20)      NULL           COMMENT '更新人',
  update_time         DATETIME         NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (user_id, medicine_id),
  KEY idx_medicine_name (medicine_name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='药品表';

-- ----------------------------------------------------------------------------
-- 3. 码表 code_mst
--    维护下拉区分数据。当前使用：吃药时间区分（code_type_id='001'）
--    说明：设计文档中"吃药时间区分"三类数据的区分ID均为001、以表示顺序区分；
--    为便于前后端传值，这里将每个选项赋予唯一 code_id（001/002/003），
--    medicine.intake_time_code 存放该 code_id。
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS t_code_mst;
CREATE TABLE t_code_mst (
  code_type_id  VARCHAR(3)   NOT NULL                COMMENT '区分类型ID（001=吃药时间区分）',
  code_id       VARCHAR(3)   NOT NULL                COMMENT '区分ID（选项唯一ID）',
  display_order INT          NOT NULL                COMMENT '表示顺序',
  code_name     VARCHAR(50)  NOT NULL                COMMENT '区分名称',
  del_flag      TINYINT      NOT NULL DEFAULT 0      COMMENT '删除标志：0-正常 1-已删除',
  PRIMARY KEY (code_type_id, code_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT='区分码表';

-- ----------------------------------------------------------------------------
-- 4. 初始数据
-- ----------------------------------------------------------------------------

-- 吃药时间区分码表数据（区分类型ID=001）
INSERT INTO t_code_mst (code_type_id, code_id, display_order, code_name) VALUES
  ('001', '001', 1, '餐前'),
  ('001', '002', 2, '随餐'),
  ('001', '003', 3, '餐后');


-- 示例药品数据（与设计文档画面示例一致）
INSERT INTO t_medicine
  (user_id, medicine_id, medicine_name, manufacturer, production_date,
   shelf_life_months, expiry_date, remaining_quantity, tablets_per_box,
   intake_time_code, daily_times, tablets_per_time, remark)
VALUES
  ('1', 'M0001', '阿司匹林', 'XXX制药厂', '2026-01-01', 24, '2028-01-01', 3, 7, '002', 1, 1, NULL),
  ('1', 'M0002', '头孢',     'OOO制药厂', '2026-04-30', 36, '2029-04-30', 1, 10, '003', 3, 2, NULL);

-- 示例用户（登录账号：admin / 123456，密码请后端加密后替换）
update t_user set login_id = 'admin',login_password = '123456' where id = 1;
update t_user set login_id = 'lisi',login_password = '123456' where id = 2;
update t_user set login_id = 'wangwu',login_password = '123456' where id = 3;
