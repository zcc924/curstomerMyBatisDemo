package com.cc.test;

import com.cc.domain.User;
import com.cc.io.Resources;
import com.cc.mapper.UserMapper;


import com.cc.session.SqlSession;
import com.cc.session.SqlSessionFactory;
import com.cc.session.SqlSessionFactoryBuilder;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class TestMyBatis {
    @Test
 public void testFindAll() {
        //怎么使用mybatis
        //1. 将主配置文件转换成字节输入流
        SqlSession sqlSession = null;
        try {
            InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
            //2.创建一个sqlsessionFactoryBulider的对象
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            //3.构建sqlsessionFactory对象
            SqlSessionFactory sqlSessionFactory = builder.build(is);//使用了构建者模式
            //4.创建sqlsession对象
            sqlSession = sqlSessionFactory.openSession();
            //5.通过sqlsession对象，创建UserMaopper接口的代理对象---->动态代理
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            //6.调用mapper对象的findAll()
            List<User> users = mapper.findAll();
            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.关闭资源
            sqlSession.close();


        }


    }
@Test
    public void show(){
        System.out.println("aaaaaaaaaaaa");
    }

}
