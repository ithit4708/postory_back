package com.jungsuk_2_1.postory.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PostLikeDao {

    boolean isLiked(Map<String, Object> params);
}
