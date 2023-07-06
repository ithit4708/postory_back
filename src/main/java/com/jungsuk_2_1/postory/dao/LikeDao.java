package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LikeDao {
    void insertLike(Map<String, Object> likeInfoMap);

    void deleteLike(Map<String, Object> likeCancleInfoMap);

    List<SubscriptionPostDto> selectLikePost(String userId);

    void updateLikeToPost(Object postId);
}
