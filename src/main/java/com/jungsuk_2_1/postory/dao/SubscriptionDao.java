package com.jungsuk_2_1.postory.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface SubscriptionDao {
    void insertSubscription(Map<String, Object> subInfo);
    void deleteSubscription(Map<String, Object> subCancleInfo);
}