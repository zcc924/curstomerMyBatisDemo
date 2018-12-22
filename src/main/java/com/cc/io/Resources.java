package com.cc.io;

import java.io.InputStream;

public class Resources {
    /**
     * 将配置文件转换成流
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
