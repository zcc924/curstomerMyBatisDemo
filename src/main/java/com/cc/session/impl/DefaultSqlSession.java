package com.cc.session.impl;

import com.cc.domain.Mapper;
import com.cc.session.Configuration;
import com.cc.session.SqlSession;
import com.cc.session.proxy.MapperProxyFactory;
import com.cc.session.utils.Converser;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    /**
     * 使用动态代理对象来获取代理对象
     * @param daoClass
     * @param <E>
     * @return
     */
    @Override
    public <E> E getMapper(Class<E> daoClass) {

        /*newProxyInstance()动态代理方法，方法需要传入三个参数：
            1.classLoader类加载器
            2.代理对象实现的接口的字节码对象的数据
            3.InvocationHandler接口的实现类对象
         */

        return (E)Proxy.newProxyInstance(daoClass.getClassLoader(),new Class[]{daoClass},new MapperProxyFactory(this));
    }

    @Override
    public void close() {

    }

    /**
     * 执行查询多条数据的aql语句
     * @param key
     * @param <E>
     * @return
     */
    @Override
    public <E> List<E> selectList(String key) {
        //1.获取连接对象
        Connection connection = null;
        try {
            connection = configuration.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //2.获取SQL语句
        Map<String, Mapper> mappers = configuration.getMappers();
        Mapper mapper = mappers.get(key);
        String sql = mapper.getSql();

        String resultType = mapper.getResultType();//JavaBean的权限命名
        //3.预编译SQL语句
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //4.执行SQL语句
            ResultSet resultSet = preparedStatement.executeQuery();
            //5.将结果集封装到JavaBean里面--->反射技术
           List<E> list= Converser.converList(resultSet,Class.forName(resultType));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }


    }

    public void setConfiguration(Configuration configuration) {
        this.configuration=configuration;
        
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
