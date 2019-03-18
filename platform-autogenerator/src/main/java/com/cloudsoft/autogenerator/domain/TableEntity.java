package com.cloudsoft.autogenerator.domain;

/**
 * @author ZJR
 * @data 2019/2/26 上午 11:38
 */
public class TableEntity {

    /**
     * 数据库表名称
     */
    private String nameFoDb;
    /**
     * 实体类表名称
     */
    private String nameFoJava;


    class TableElement{

        private String columnNameFoDb;
        private String columnNameFoJava;




    }
}
