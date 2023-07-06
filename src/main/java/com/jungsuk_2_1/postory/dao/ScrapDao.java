package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScrapDao {
    void insertScrap(Map<String, Object> scrapInfoMap);
    void deleteScrap (Map<String, Object> scrapCancleInfoMap);
    List<SubscriptionPostDto> selectScrapPost(String userId);
}
