package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ChannelPostDto;
import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.dto.StudioPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao {

    List<ChannelPostDto> getPostsByChnlUri(Map<String, Object> params);
    void createPost(Map<String, Object> params);
    Integer findLastId();
    Integer findInSeries(Map<String, Object> params);
    Integer findInNonSeries();
    StudioPostDto findInStudioByChnlUri(String chnlUri);
    void updateNextPostId(Map<String,Object> params);
    StudioPostDto findById(Integer postId);

    void updatePost(Map<String, Object> params);
}
