package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dao.SeriesDao;
import com.jungsuk_2_1.postory.dto.ChannelPostDto;
import com.jungsuk_2_1.postory.dto.PostDto;
import com.jungsuk_2_1.postory.dto.StudioPostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PostService {

  private final PostDao postDao;

  private final SeriesDao seriesDao;



  //  ChannelDao channelDao;
  @Autowired
  PostService(PostDao postDao, SeriesDao seriesDao) {
    this.postDao = postDao;
    this.seriesDao = seriesDao;
  }
  public StudioPostDto createPost(PostDto postDto) {

    int newPostId = postDao.findLastId() + 1;
    int befPostId;

    befPostId = getMaxPostIdinSer(postDto.getChnlUri(),postDto.getSerId());

    Map<String, Object> params = new HashMap<>();
    params.put("name", "createPost");
    params.put("postId", newPostId);
    params.put("postTtl", postDto.getPostTtl());
    params.put("postSbTtl", postDto.getPostSbTtl());
    params.put("postPchrgYn", postDto.getPostPchrgYn());
    params.put("postThumnPath", postDto.getPostThumnPath());
    params.put("postSerId", postDto.getSerId());
    params.put("pchrgBlkPurcPnt", postDto.getPchrgBlkPurcPnt());
    params.put("ntceSettYn", postDto.getNtceSettYn());
    params.put("adoYn", postDto.getAdoYn());
    params.put("chnlId", postDto.getChnlId());
    params.put("chnlUri", postDto.getChnlUri());
    params.put("basicFontCd", postDto.getBasicFontCd());
    params.put("basicParagAlgnCd", postDto.getBasicParagAlgnCd());
    params.put("itdYn", postDto.getItdYn());
    params.put("paragGapMargYn", postDto.getParagGapMargYn());
    params.put("nowPostStusCd", postDto.getNowPostStusCd());
    params.put("nowPostStusChgrId", postDto.getNowPostStusChgrId());
    params.put("befPostId", befPostId);
    params.put("nextPostId", null);

    postDao.createPost(params);

    Map<String, Object> args = new HashMap<>();
    args.put("postId", befPostId);
    args.put("nextPostId", postDao.findLastId());
    postDao.updateNextPostId(args);

    return getPostsInStudio(postDto.getChnlUri());
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

  public StudioPostDto getPostsInStudio(String chnlUri){

    return postDao.findInStudioByChnlUri(chnlUri);
  }

  private int getMaxPostIdinSer(String chnlUri, int serId) {
    Map<String, Object> params = new HashMap<>();
    params.put("chnlUri", chnlUri);
    params.put("serId", serId);
    return postDao.findInSeries(params);
  }


}
