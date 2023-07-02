package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.SubscriptionChannelDto;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubscriptionDao {
    void insertSubscription(Map<String, Object> subInfo);

    void deleteSubscription(Map<String, Object> subCancleInfo);

    List<SubscriptionPostDto> selectSubscriptionPost(String userId);

    List<SubscriptionChannelDto> selectSubscriptionChannel(String userId);
}