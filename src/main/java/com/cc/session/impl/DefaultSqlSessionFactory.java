package com.cc.session.impl;

import com.cc.session.Configuration;
import com.cc.session.SqlSession;
import com.cc.session.SqlSessionFactory;
import com.cc.session.utils.XmlConfigBuilder;

import java.io.InputStream;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private InputStream is;

    @Override
    public SqlSession openSession() {
        //创建一个SqlSession对象
        DefaultSqlSession sqlSession=new DefaultSqlSession();

        //开始解析主配置文件
        Configuration configuration = XmlConfigBuilder.loadSqlMapConfig(is);
        sqlSession.setConfiguration(configuration);
        return sqlSession;
    }

    public void setIs(InputStream is) {
        this.is=is;
        
        
    }
}
