package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;

import java.util.List;
import java.util.Map;

public interface ScrapService {
    void addToScrapList(Map<String, Object> scrapInfoMap);
    void removeFromScrapList(Map<String, Object> scrapCancleInfoMap);
    List<SubscriptionPostDto> getScrapPostList(String userId);
}