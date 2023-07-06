package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;

import java.util.List;
import java.util.Map;

public interface LikeService {
    void addToLikeList(Map<String, Object> likeInfoMap);
    void removeFromLikeList(Map<String, Object> likeCancleInfoMap);
    List<SubscriptionPostDto> getLikePostList(String userId);
}
