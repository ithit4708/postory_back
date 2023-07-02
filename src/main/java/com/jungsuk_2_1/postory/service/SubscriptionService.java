package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.SubscriptionChannelDto;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;

import java.util.List;
import java.util.Map;

public interface SubscriptionService {
    void addToSubscriptionList(Map<String, Object> subInfo);

    void removeFromSubscriptionList(Map<String, Object> subCancleInfo);

    List<SubscriptionPostDto> getSubscriptionPostList(String userId);
    List<SubscriptionChannelDto> getSubscriptionChannelList(String userId);
}
