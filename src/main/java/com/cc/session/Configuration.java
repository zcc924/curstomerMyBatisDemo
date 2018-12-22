package com.cc.session;


import com.cc.domain.Mapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;



import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: curstomerMyBatisDemo
 * @description: 获取connection对象
 * @author: 张楚楚
 * @create: 2018-12-21 19:06
 **/

public class Configuration {

  private String driver;
  private String username;
  private String password;
  private String url;

    private  Map<String, Mapper> mappers=new HashMap<String,Mapper>();

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * 获取链接对象
     * @return
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "driver='" + driver + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public void setMappers(Map<String, Mapper> mappers) {
        //每次都是合并Map而不是替换
        this.mappers.putAll(mappers);
    }
    public Map<String, Mapper> getMappers() {
        return mappers;
    }
}
