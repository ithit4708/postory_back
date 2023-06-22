package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao {

    List<PostDto> getPostsByChnlUri(Map<String, Object> params);


    List<PostDto> createPost();
}
