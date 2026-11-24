package com.example.demo.common;



import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.Collections;

public class CodeGenerator {
public static void main(String[] args) {
         FastAutoGenerator.create(
                                        "jdbc:mysql://localhost:3306/demo_db?useUnicode=true&characterEncoding=utf8"
                                                + "&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true",
                         "root", "1234567890")
                 // 全局配置
                 .globalConfig(b -> b
                         .author("yourName")
                         .disableOpenDir()
                         .outputDir(System.getProperty("user.dir") + "/src/main/java")
                 )
                 // 包配置
                 .packageConfig(b -> b
                         .parent("com.example.demo")
                         .moduleName("") // 模块名，无则空
                         .entity("entity")
                         .mapper("mapper")
                         .service("service")
                         .serviceImpl("service.impl")
                         .controller("controller")
                         .xml("mapper.xml") // XML 包名/路径后缀
                         // 若需将 XML 生成到 resources/mapper 下，用 pathInfo：
                         .pathInfo(Collections.singletonMap(OutputFile.xml,
                                 System.getProperty("user.dir") + "/src/main/resources/mapper"))
                 )
                 // 策略配置
                 .strategyConfig(b -> b
                         .addInclude("t_medical_record") // 表名
                         .addTablePrefix("t_") // 表前缀
                         .entityBuilder()
                         .naming(NamingStrategy.underline_to_camel)
                         .columnNaming(NamingStrategy.underline_to_camel)
                         .enableLombok() // 若用 lombok
                         .controllerBuilder()
                         .enableRestStyle()
                         .mapperBuilder()
                         .enableFileOverride() // 覆盖旧文件
                 )
                 // 模板引擎（你已引 velocity，可写 new VelocityTemplateEngine()，默认也是 velocity）
                 .execute();
     }
 }
