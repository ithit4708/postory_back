package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.ScrapDao;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScrapServiceImpl implements ScrapService {
    ScrapDao scrapDao;

    ScrapServiceImpl(ScrapDao scrapDao) {
        this.scrapDao = scrapDao;
    }

    @Override
    public void addToScrapList(Map<String, Object> scrapInfoMap) {
        scrapDao.insertScrap(scrapInfoMap);
    }

    @Override
    public void removeFromScrapList(Map<String, Object> scrapCancleInfoMap) {
        scrapDao.deleteScrap(scrapCancleInfoMap);
    }

    @Override
    public List<SubscriptionPostDto> getScrapPostList(String userId) {
        return scrapDao.selectScrapPost(userId);
    }
}
