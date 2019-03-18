package com.cloudsoft.autogenerator.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 * @author ZJR
 * @data 2019/2/19 上午 10:52
 */
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {



    public static AutoGenerator pageAutoGenerator(){
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir"));
        globalConfig.setFileOverride(true);
        globalConfig.setActiveRecord(true);
        globalConfig.setEnableCache(false);// XML 二级缓存
        globalConfig.setBaseResultMap(true);// XML ResultMap
        globalConfig.setBaseColumnList(false);// XML columList
        globalConfig.setAuthor("ZJR");
        //生成文件名:
        globalConfig.setEntityName("%sBO");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setMapperName("%sDao");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sImpl");
        globalConfig.setControllerName("%sController");
        autoGenerator.setGlobalConfig(globalConfig);



        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("");  这里是集成某个实体类，例如我实体类每一个都有id，写一个base，可以这里设置生成的集成关系
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setSuperControllerClass("com.bankpay.controller.BaseController");
        strategy.setInclude(new String[]{"account"});//输入你要生成的表名字
//        strategy.setSuperEntityColumns("id"); //公共字段
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setEntityLombokModel(true);
        autoGenerator.setStrategy(strategy);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        autoGenerator.setTemplateEngine(new VelocityTemplateEngine());
        autoGenerator.setTemplate(templateConfig);




        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://192.168.141.128:3309/bank_pay?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        autoGenerator.setDataSource(dataSourceConfig);



        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("src.main");
//        packageConfig.setModuleName("java.com.bankpay.domain.bo");
        packageConfig.setEntity("java.com.bankpay.domain.bo");
        packageConfig.setController("java.com.bankpay.controller");
        packageConfig.setService("java.com.bankpay.service");
        packageConfig.setServiceImpl("java.com.bankpay.service.impl");
        packageConfig.setMapper("java.com.bankpay.dao");
        packageConfig.setXml("resources.mapper");
        autoGenerator.setPackageInfo(packageConfig);









        return autoGenerator;
    }

    public static void main(String[] args) {
        AutoGenerator ag = pageAutoGenerator();
        ag.execute();
    }

}
