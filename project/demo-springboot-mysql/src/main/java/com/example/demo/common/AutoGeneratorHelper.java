package com.example.demo.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class AutoGeneratorHelper {

    public static void main(String[] args) throws SQLException {
        AutoGenerator autoGenerator = new AutoGenerator();
        System.out.println("start execute.......");

        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setOutputDir("src\\main\\java");
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(true);
        globalConfig.setEnableCache(false);
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        globalConfig.setAuthor("manager");
        globalConfig.setEntityName("%s");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        // globalConfig.setControllerName("%sGenController");
        autoGenerator.setGlobalConfig(globalConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
//        dataSourceConfig.setDriverName("oracle.jdbc.OracleDriver");
//        dataSourceConfig.setUsername("ITOD_002");
//        dataSourceConfig.setPassword("ITO_DEV_21d");
//        dataSourceConfig.setUrl("jdbc:oracle:thin:@10.103.25.3:1521/DEVITT_APPS_SRV");
		dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
		dataSourceConfig.setUsername("root");
		dataSourceConfig.setPassword("1234567890");
		dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/demo_db?useUnicode=true&characterEncoding=utf8"
				+ "&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        autoGenerator.setDataSource(dataSourceConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        //strategyConfig.setCapitalMode(true);
        strategyConfig.setTablePrefix(new String[]{"QRTZ_"});
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setInclude(new String[]{
                "T_APLINF","T_APLINFOHIS","T_BUKKENINPUTHIS","T_PROPERTY_SOSAIINF"
		});
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setSuperServiceClass("jp.co.smfl.ito.framework.service.BaseService");
        strategyConfig.setSuperServiceImplClass("jp.co.smfl.ito.framework.service.impl.BaseServiceImpl");
        autoGenerator.setStrategy(strategyConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("jp.co.smfl.ito.api.common.model");
        //packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setMapper("dao");
        // packageConfig.setXml(System.getProperty("user.dir") + "/src/main/resources/mybatis/mapper");
        autoGenerator.setPackageInfo(packageConfig);
        TemplateConfig template = new TemplateConfig();
        template.setController("");
        template.setXml("");
        template.setService("/templates/service.java.vm");
        template.setServiceImpl("/templates/serviceImpl.java.vm");
        template.setEntity("/templates/entity.java.vm");
        template.setMapper("/templates/mapper.java.vm");
        autoGenerator.setTemplate(template);
        autoGenerator.execute();
		System.out.println(autoGenerator.getDataSource().getConn());
		//--------------------------Modify Key column--------------------------//
		Connection con = autoGenerator.getDataSource().getConn();
		String filePath = autoGenerator.getGlobalConfig().getOutputDir()+ "jp/co/smfl/ito/api/common/model/entity/";
    	String keySql = "select col.column_name from user_constraints con,user_cons_columns col where con.constraint_name=col.constraint_name and con.constraint_type='P' and col.table_name=?";
		String[] tableNames = autoGenerator.getStrategy().getInclude();
		for(String tableName: tableNames) {
			 PreparedStatement pst = null;
	         ResultSet rs = null;
			 File file = new File(filePath + CamelCaseUtils.toCapitalizeCamelCase(tableName) + ".java");
			 List<String> keyCol = new ArrayList<>();
	         try {
	             pst = con.prepareStatement(keySql);
	             pst.setString(1, tableName);
	             rs = pst.executeQuery();
	             while(rs.next()){
					 keyCol.add(rs.getString("COLUMN_NAME"));
	             }
	         } catch (SQLException ex) {
				ex.printStackTrace();
	         } finally{
	            if(rs != null){
	               rs.close();
	            }
	            if(pst != null){
	               pst.close();
	            }
	         }
			new AutoGeneratorHelper().operationFile(file, keyCol);
		}

		System.out.println("AutoGeneratorHelper: Entity create end！" );
	}


	public String getPath(){
		return this.getClass().getResource("/").getPath();
	}

	public void operationFile(File file, List<String> target) {

		try {
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String filename = file.getName();
			File tmpfile = new File(file.getParentFile().getAbsolutePath()+ "\\" + filename + ".tmp");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tmpfile));
			boolean flag = false;
			String str = null;
			while (true) {
				str = reader.readLine();
				if (str == null)
					break;
				boolean isNeedReplace = false;
				String repCol = "";
				for(String col : target) {
					if (str.contains("@TableField(\""+ col +"\")")) {
						isNeedReplace = true;
						repCol = col;
					}
				}
				if(isNeedReplace) {
					writer.write(str.replace("@TableField(\""+ repCol +"\")", "@TableId(\""+ repCol +"\")") + "\n");
					System.out.println(file.getAbsolutePath() + ":" + "@TableField(\""+ repCol +"\") ->>" + "@TableId(\""+ repCol +"\")");
					flag = true;
				}else {
					writer.write(str + "\n");
				}
			}
			is.close();
			writer.flush();
			writer.close();
			if (flag) {
				file.delete();
				tmpfile.renameTo(new File(file.getAbsolutePath()));
			} else {
				tmpfile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
