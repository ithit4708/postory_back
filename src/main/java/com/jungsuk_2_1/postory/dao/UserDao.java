package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ChannelUserDto;
import com.jungsuk_2_1.postory.dto.HeaderChannelDto;
import com.jungsuk_2_1.postory.dto.HeaderUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    UserDto findByUserEmail(String eId);
    Boolean existsByUserEmail(String eId);
    Boolean existsByUserNic(String nic);
    void save(UserDto userDto);
    void statusSave(String userId);
    void emailAuthSave(UserDto user);
    String findStatusByUserId(String userId);
    UserDto findByUserId(String userId);
    ChannelUserDto findByChnlUri(String channelUri);
    List<HeaderChannelDto> findHeaderInfoByUserId(String userId);
    HeaderUserDto findHeaderUserInfoByUserId(String userId);

    ChannelUserDto findByPostId(Integer postId);
}