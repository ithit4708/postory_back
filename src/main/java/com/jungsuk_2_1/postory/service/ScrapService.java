package com.jungsuk_2_1.postory.service;

import java.util.Map;

public interface ScrapService {
    void addToScrapList(Map<String, Object> scrapInfoMap);
    void removeFromScrapList(Map<String, Object> scrapCancleInfoMap);
}