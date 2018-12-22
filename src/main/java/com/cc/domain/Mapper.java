package com.cc.domain;

/**
 * @program: curstomerMyBatisDemo
 * @description: 存储sql信息及resultType信息
 * @author: 张楚楚
 * @create: 2018-12-21 23:02
 **/

public class Mapper {
   private String sql;
   private String resultType;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

}
