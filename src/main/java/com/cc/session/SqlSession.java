package com.cc.session;

import com.cc.mapper.UserMapper;

import java.util.List;

public interface SqlSession {
    <E> E getMapper(Class<E> daoClass);//可以获取一切的mapper代理对象，所以使用泛型

    void close();

    /**
     * 执行查询多条语句的sql
     * @param key
     * @param <E>
     * @return
     */
    <E> List<E> selectList(String key);
}
