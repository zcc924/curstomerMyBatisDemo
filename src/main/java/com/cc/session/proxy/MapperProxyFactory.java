package com.cc.session.proxy;

import com.cc.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @program: curstomerMyBatisDemo
 * @description:
 * @author: 张楚楚
 * @create: 2018-12-22 09:25
 **/

public class MapperProxyFactory implements InvocationHandler {
    private SqlSession sqlSession;

    public MapperProxyFactory(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果返回值是一个list集合才执行如下代码
        Class<?> returnType = method.getReturnType();
        if (returnType==List.class) {
            //代理对象调用任何方法，都会在这执行
            //真正执行SQL语句
            //执行SQL语句的工作：sqlSession对象的selectList（）方法，也就是说要调用那个方法
            //key是"Mapper"接口的全限定名+"."+方法名
            String methodName = method.getName();//获取方法名

            Class<?> declaringClass = method.getDeclaringClass();
            String className = declaringClass.getName();//获取接口的全限定名
            String key = className + "." + methodName;
            List<Object> list = sqlSession.selectList(key);
            return list;
        }else {
            //其他情况日后考虑
            return null;
        }
    }
}
