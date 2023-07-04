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
        //subscription 테이블에 해당 채널이 추가 됨
        subscriptionDao.insertSubscription(subInfo);
        //channel 테이블에 해당 채널의 구독자 수가 count되어 update 되어야 함(+).
        subscriptionDao.updateSubscriberToChannel(subInfo.get("chnlId"));
    }

    @Override
    public void removeFromSubscriptionList(Map<String, Object> subCancleInfo) {
        //subscription 테이블에 해당 채널이 제거 됨
        subscriptionDao.deleteSubscription(subCancleInfo);
        //channel 테이블에 해당 채널의 구독자 수가 count되어 update 되어야 함(-).
        subscriptionDao.updateSubscriberToChannel(subCancleInfo.get("chnlId"));
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
