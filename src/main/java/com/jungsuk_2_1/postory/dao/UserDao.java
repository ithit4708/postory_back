package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserDto findByUserEmail(String userEid);
    Boolean existsByUserEmail(String userEid);
    void save(UserDto userDto);
}