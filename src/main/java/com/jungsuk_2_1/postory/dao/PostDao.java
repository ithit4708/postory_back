package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PostDao {

    List<ChannelPostDto> getPostsByChnlUri(Map<String, Object> params);
    void createPost(Map<String, Object> params);
    Integer findLastId();
    Integer findInSeries(Map<String, Object> params);
    Integer findInNonSeries(Map<String, Object> params);
    StudioPostDto findInStudioByChnlUri(String chnlUri);
    void updateNextPostId(Map<String,Object> params);
    PostDto findById(Integer postId);

    PostRelatedDto findRelatedPostById(Integer postId);

    void updatePost(Map<String, Object> params);

    void deletePost(Integer postId);
    boolean doesExist(Integer postId);

    void updateNextPostBefPostId(Integer postId,Integer befPostId);

    void updateBefPostNextPostId(Integer postId, Integer nextPostId);

    List<OnlyIdDto> findIdBySerId(Integer serId);

    void deletePostByChnlId(Integer chnlId);

    void deletePostBySerId(Integer serId);

    List<OnlyIdDto> findIdByChnlId(Integer chnlId);

    List<ChannelPostDto> fingPostsBySerId(Integer serId);

    PostViewDto findByIdInContent(Integer postId);

    boolean checkUser(Map<String, Object> params);

    void increaseViewCount(Integer postId);

    ChannelSimpleDto findChannelByPostId(Integer postId);

    List<ChannelPostDto> getPostsBySc(Map<String,Object> params);
}
