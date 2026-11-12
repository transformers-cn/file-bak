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
