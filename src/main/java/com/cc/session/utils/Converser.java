package com.cc.session.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: curstomerMyBatisDemo
 * @description: 封装结果集工具类
 * @author: 张楚楚
 * @create: 2018-12-21 23:46
 **/

public class Converser {
    /**
     * 这个方法，是将结果集中的每一条数据封装到一个JavaBean中，多条数据就对应多个JavaBean
     * 再将多个JavaBean放到一个List集合中
     * @param set
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> List<E> converList(ResultSet set,Class clazz){

        List<E> beans=new ArrayList<E>();
        //1.遍历结果集

        try {
            //根据结果集数据，获取结果集中的每一列的列名
            ResultSetMetaData metaData = set.getMetaData();
            //获取总列数
            int columnCount = metaData.getColumnCount();

            while(set.next()){
               //每次遍历，遍历出一条数据，每条数据就对应一个JavaBean对象
                E e = (E)clazz.newInstance();

                //获取每一列数据，根据列名获取
                //for循环遍历出每一列
                for (int i = 1; i <=columnCount ; i++) {
                    String columnName = metaData.getColumnName(i);
                    //获取该列的值
                    Object value = set.getObject(columnName);
                    //将该列的值存放到JavaBean中
                    //也就是调用JavaBean的set方法，使用内省机制
                    PropertyDescriptor descriptor=new PropertyDescriptor(columnName,clazz);
                    Method method=descriptor.getWriteMethod();//调用该属性的set方法

                    //调用set方法
                    method.invoke(e,value);
                }
                //经过这个for循环，JavaBean就设置好了，把JavaBean添加进List集合
                beans.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return beans;
    }



}
