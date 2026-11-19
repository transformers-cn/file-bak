//package com.example.demo.common;
//
//
//
//import com.baomidou.mybatisplus.generator.FastAutoGenerator;
//import com.baomidou.mybatisplus.generator.config.OutputConfig;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import java.util.Collections;
//
//public class CodeGenerator {
//public static void main(String[] args) {
//         FastAutoGenerator.create(
//                                        "jdbc:mysql://localhost:3306/demo_db?useUnicode=true&characterEncoding=utf8"
//                                                + "&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
//                         "root", "1234567890")
//                 .globalConfig(b -> b
//                                        .author("yourName")
//                                        .disableOpenDir()
//                                        // ★ 输出到项目源码目录（Windows 用反斜杠或双斜杠）
//                         .outputDir("src/main/java"))
//                 .packageConfig(b -> b
//                                        .parent("com.example.demo")
//                                        .moduleName("")            // 不要多出 demo 层级
//                                        .entity("entity").mapper("mapper")
//                                        .service("service").serviceImpl("service.impl")
//                                        .controller("controller")
//                                        // ★ XML 不生成到 java 目录，改到 resources/mapper
//                         .pathInfo(Collections.singletonMap(
//                                                OutputConfig.xml(), "src/main/resources/mapper")))
//                 .strategyConfig(b -> b
//                                        .addInclude("t_medical_record")     // ★ 指定表名，可多张
//                                        .addTablePrefix("t_")               // ★ 去掉表前缀
//                                        .entityBuilder()
//                                        .naming(NamingStrategy.underline_to_camel)
//                                        .columnNaming(NamingStrategy.underline_to_camel)
//                                        .logicDeleteColumnName("del_flag")  // 逻辑删除列
//                                        .controllerBuilder().enableRestStyle()
//                                        .enableFileOverride())              // 覆盖旧文件
//                 .execute();
//     }
// }
