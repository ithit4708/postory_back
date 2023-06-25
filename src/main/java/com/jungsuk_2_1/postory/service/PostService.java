package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dto.ChannelPostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PostService {

  PostDao postDao;


  //  ChannelDao channelDao;
  @Autowired
  PostService(PostDao postDao) {
    this.postDao = postDao;
  }

  public List<ChannelPostDto> createPost(){
    return postDao.createPost();
  }

  public List<ChannelPostDto> getPostsByChnlUri(String chnlUri, int page, String orderMethod, int pageSize) {

    Map<String, Object> params = new HashMap<>();
    params.put("name", "channelPosts");
    params.put("chnlUri", chnlUri);
    params.put("pageSize", pageSize);
    params.put("offset", (page - 1) * pageSize);
    params.put("orderMethod", orderMethod);

    return postDao.getPostsByChnlUri(params);
  }
}
