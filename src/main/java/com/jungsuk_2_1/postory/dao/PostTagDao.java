package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.OnlyIdDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface PostTagDao {
    void createPostTag(Map<String,Object> params);

    OnlyIdDto findByPostId(Integer postId);
}
