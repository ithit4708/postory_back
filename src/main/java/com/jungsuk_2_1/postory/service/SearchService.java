package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.ChannelDao;
import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dto.ChannelPostDto;
import com.jungsuk_2_1.postory.dto.SearchChannelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SearchService {
    private PostDao postDao;
    private ChannelDao channelDao;
    @Autowired
    SearchService(PostDao postDao, ChannelDao channelDao){
        this.postDao = postDao;
        this.channelDao = channelDao;
    }

    public List<SearchChannelDto> searchChannel(Map<String, Object> params){

        System.out.println("params = " + params);
        List<SearchChannelDto> dtos = channelDao.findChannel(params);
        return dtos;
    }

    public List<ChannelPostDto> searchPost(String userId, String option, String keyword, String postType, int page, String orderMethod, int pageSize){
        String postTypeNm = (postType.equals("webtoon") ? "웹툰" : "웹소설");

        Map<String, Object> params = new HashMap<>();
        params.put("name", "searchPosts");
        params.put("userId", userId);

        params.put("pageSize", pageSize);
        params.put("offset", (page - 1) * pageSize);
        params.put("postType",postTypeNm);
        params.put("orderMethod", orderMethod);
        params.put("option",option);
        params.put("keyword",keyword);
        System.out.println("params = " + params);
        List<ChannelPostDto> dto = postDao.getPostsBySc(params);
        System.out.println("dto = " + dto);
        return dto;
    }
}
