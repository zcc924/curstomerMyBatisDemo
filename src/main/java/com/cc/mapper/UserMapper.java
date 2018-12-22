package com.cc.mapper;

import com.cc.domain.User;

import java.util.List;

public interface UserMapper {
//查找所有数据
    List<User>  findAll();
}
