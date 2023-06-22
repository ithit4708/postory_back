package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dto.PostDto;
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

  public List<PostDto> createPost(){
    return postDao.createPost();
  }

  public List<PostDto> getPostsByChnlUri(String chnlUri, int page, String orderMethod) {

    Map<String, Object> params = new HashMap<>();
    params.put("chnlUri", chnlUri);
    params.put("pageSize", 12);
    params.put("offset", (page - 1) * 12);
    params.put("orderMethod", orderMethod);

    return postDao.getPostsByChnlUri(params);
  }
}
