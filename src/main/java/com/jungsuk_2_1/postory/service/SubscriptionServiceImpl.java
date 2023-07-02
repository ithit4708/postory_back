package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.SubscriptionDao;
import com.jungsuk_2_1.postory.dto.SubscriptionChannelDto;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    SubscriptionDao subscriptionDao;
    SubscriptionServiceImpl(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }
    @Override
    public void addToSubscriptionList(Map<String, Object> subInfo) {
        subscriptionDao.insertSubscription(subInfo);
    }

    @Override
    public void removeFromSubscriptionList(Map<String, Object> subCancleInfo) {
        subscriptionDao.deleteSubscription(subCancleInfo);
    }

    @Override
    public List<SubscriptionPostDto> getSubscriptionPostList(String userId) {
        return subscriptionDao.selectSubscriptionPost(userId);
    }

    @Override
    public List<SubscriptionChannelDto> getSubscriptionChannelList(String userId) {
        return subscriptionDao.selectSubscriptionChannel(userId);
    }
}
