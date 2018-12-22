package com.cc.session.utils;

import com.cc.domain.Mapper;
import com.cc.io.Resources;
import com.cc.session.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析配置：主配置文件和映射配置文件
 */
public class XmlConfigBuilder {

    public  static  Configuration loadSqlMapConfig(InputStream is){
        //解析主配置文件，使用Dom4j和XPATH解析

        //1.创建一个SAXReader
        SAXReader reader=new SAXReader();
        //2.读取配置文件，转换成一个document对象‘
        try {
            Document document = reader.read(is);
            //目标：获取主配置文件中的dirver、url、user、password 最终目标：获取connection对象
            //获取所有的property标签
            List<Element> propertyElements = document.selectNodes("//property");

            //创建一个configuration对象用于存放数据
            Configuration configuration = new Configuration();
            //遍历每一个property标签
            for (Element propertyElement:propertyElements ) {
              //获取它的name属性值和value属性值
                String name = propertyElement.attributeValue("name");//name属性值
                String value = propertyElement.attributeValue("value");//value属性值
                //通过name来知晓获取的是什么数据
                if ("username".equals(name)) {
                    System.out.println("username-value:::"+value);
                    configuration.setUsername(value);
                }
               if ("password".equals(name)){
                   configuration.setPassword(value);
               }
                if ("url".equals(name)) {
                    configuration.setUrl(value);
                }
                if ("driver".equals(name)) {
                    System.out.println("driver-value:::"+value);
                    configuration.setDriver(value);
                }
            }

            //获取所有的mapper标签----->resource属性就是映射文件的路径
            List<Element> mapperElements = document.selectNodes("//mapper");
            //遍历出每个mapper标签
            for (Element mapperElement : mapperElements) {
                String resource = mapperElement.attributeValue("resource");//resource就是映射配置文件的路径

                //解析映射配置文件
                Map<String, Mapper> mappers = loadMapper(resource);
                configuration.setMappers(mappers);

            }
                return configuration;
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 加载映射配置文件
     * @param resource
     * @return
     */
    private static  Map<String,Mapper> loadMapper(String resource){
        //声明一个Map集合
        Map<String,Mapper> mappers=new HashMap<>();

        //解析映射配置文件
        //使用DOM4j解析xml
        SAXReader reader = new SAXReader();
        InputStream inputStream = Resources.getResourceAsStream(resource);
        try {
            Document document = reader.read(inputStream);
            //1.获取根标签，mapper标签
            Element rootElement = document.getRootElement();
            //2.获取根标签的namespace属性
            String namespace = rootElement.attributeValue("namespace");
            //获取所有的select标签
            List<Element> selectElements = document.selectNodes("//select");
            for (Element selectElement : selectElements) {
                //遍历出每一个select标签
                //获取id，id就是这个select标签的唯一标签，对应mapper接口中的方法名
                String id = selectElement.attributeValue("id");
                //目标：获取SQL语句以及resultType
                String resultType = selectElement.attributeValue("resultType");
                String sql = selectElement.getTextTrim();//去除sql前后的空格
                //每一个select标签就需要mapper对象来存放数据
                Mapper mapper=new Mapper();
                mapper.setSql(sql);
                mapper.setResultType(resultType);
                //通过分析我们知道每一个mapper对象对应一个唯一的id ---->map集合
                mappers.put(namespace+"."+id,mapper);

            }

            return mappers;

        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
