package com.cc.session;

import com.cc.session.impl.DefaultSqlSessionFactory;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream is) {
        DefaultSqlSessionFactory defaultSqlSessionFactory=new DefaultSqlSessionFactory();
        defaultSqlSessionFactory.setIs(is);
        return defaultSqlSessionFactory;

    }
}
