package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    UserDto findByUserEmail(String eId);
    Boolean existsByUserEmail(String eId);
    void save(UserDto userDto);
    void statusSave(String userId);
    String findStatusByUserId(String userId);
}