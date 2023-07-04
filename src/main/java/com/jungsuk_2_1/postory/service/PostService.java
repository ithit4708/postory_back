package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dao.PostTagDao;
import com.jungsuk_2_1.postory.dao.SeriesDao;
import com.jungsuk_2_1.postory.dto.*;
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
  private final PostTagDao postTagDao;

  @Autowired
  PostService(PostDao postDao, SeriesDao seriesDao, PostTagDao postTagDao) {
    this.postDao = postDao;
    this.seriesDao = seriesDao;
    this.postTagDao = postTagDao;
  }
  public StudioPostDto createPost (String userId, PostDto postDto){

    Integer newPostId = postDao.findLastId() + 1;

    System.out.println("newPostId = " + newPostId);

    Integer befPostId;

    befPostId = getMaxPostIdInSer(postDto.getChnlUri(), postDto.getSerId());

    System.out.println("befPostId = " + befPostId);


    Map<String, Object> params = new HashMap<>();
    params.put("name", "createPost");
    params.put("postId", newPostId);
    params.put("postTtl", postDto.getPostTtl());
    params.put("postSbTtl", postDto.getPostSbTtl());
    params.put("postPchrgYn", postDto.getPostPchrgYn());
    params.put("postThumnPath", postDto.getPostThumnPath());
    params.put("serId", postDto.getSerId());
    params.put("pchrgBlkPurcPnt", postDto.getPchrgBlkPurcPnt());
    params.put("ntceSettYn", postDto.getNtceSettYn());
    params.put("adoYn", postDto.getAdoYn());
    params.put("chnlId", postDto.getChnlId());
    params.put("chnlUri", postDto.getChnlUri());
    params.put("basicFontCdNm", postDto.getBasicFontCdNm());
    params.put("basicParagAlgnCdNm", postDto.getBasicParagAlgnCdNm());
    params.put("itdYn", postDto.getItdYn());
    params.put("paragGapMargYn", postDto.getParagGapMargYn());
    params.put("nowPostStusCdNm", postDto.getNowPostStusCdNm());
    params.put("nowPostStusChgrId", userId);
    params.put("befPostId", befPostId);
    params.put("nextPostId", null);

    postDao.createPost(params);

    Map<String, Object> args = new HashMap<>();
    args.put("postId", befPostId);
    args.put("nextPostId", postDao.findLastId());
    postDao.updateNextPostId(args);

    Map<String, Object> data = new HashMap<>();
    data.put("name","createPostTag");
    data.put("postId",newPostId);
    data.put("postType",postDto.getPostType());
    postTagDao.createPostTag(data);

    OnlyIdDto id = postTagDao.findByPostId(newPostId);
    StudioPostDto dto = postDao.findById(newPostId);

    if (id.getId() == 1) {
      dto.setPostType("웹소설");
    } else if (id.getId() == 2){
      dto.setPostType("웹툰");
    }


    return dto;
  }

  public List<ChannelPostDto> getPostsByChnlUri (String chnlUri,int page, String orderMethod,int pageSize){

    Map<String, Object> params = new HashMap<>();
    params.put("name", "channelPosts");
    params.put("chnlUri", chnlUri);
    params.put("pageSize", pageSize);
    params.put("offset", (page - 1) * pageSize);
    params.put("orderMethod", orderMethod);

    return postDao.getPostsByChnlUri(params);
  }

  public StudioPostDto getPostInStudio (String chnlUri){

    return postDao.findInStudioByChnlUri(chnlUri);
  }

  private Integer getMaxPostIdInSer (String chnlUri, Integer serId){
    Map<String, Object> params = new HashMap<>();
    params.put("chnlUri", chnlUri);
    params.put("serId", serId);

    System.out.println("params = " + params);
    System.out.println("serId = " + serId);

    if (serId == null){
      Integer maxId = postDao.findInNonSeries(params);

      System.out.println("maxId = " + maxId);

      return maxId;
    } else {
      Integer maxId = postDao.findInSeries(params);

      System.out.println("maxId = " + maxId);
      return maxId;
    }

  }

  public StudioPostDto updatePost (String userId, Integer postId, PostDto postDto){


    Map<String, Object> params = new HashMap<>();
    params.put("name", "editPost");
    params.put("postId", postId);
    params.put("postTtl", postDto.getPostTtl());
    params.put("postSbTtl", postDto.getPostSbTtl());
    params.put("postPchrgYn", postDto.getPostPchrgYn());
    params.put("postThumnPath", postDto.getPostThumnPath());
    params.put("pchrgBlkPurcPnt", postDto.getPchrgBlkPurcPnt());
    params.put("ntceSettYn", postDto.getNtceSettYn());
    params.put("adoYn", postDto.getAdoYn());
    params.put("basicFontCdNm", postDto.getBasicFontCdNm());
    params.put("basicParagAlgnCdNm", postDto.getBasicParagAlgnCdNm());
    params.put("itdYn", postDto.getItdYn());
    params.put("paragGapMargYn", postDto.getParagGapMargYn());
    params.put("nowPostStusCdNm", postDto.getNowPostStusCdNm());
    params.put("nowPostStusChgrId", userId);

    postDao.updatePost(params);
    return postDao.findById(postId);
  }
  public boolean deletePost(Integer postId){

    PostRelatedDto postRelatedDto = postDao.findRelatedPostById(postId);
    postDao.updateNextPostBefPostId(postRelatedDto.getNextPostId(),postRelatedDto.getBefPostId());
    postDao.updateBefPostNextPostId(postRelatedDto.getBefPostId(),postRelatedDto.getNextPostId());
    postDao.deletePost(postId);
    return postDao.doesExist(postId);
  }

    public List<ChannelPostDto> getPostsBySerId(Integer serId) {
    return postDao.fingPostsBySerId(serId);
    }

  public ContentPostDto readPostById(String userId, Integer postId) {
    postDao.increaseViewCount(postId);

    Map<String, Object> params = new HashMap<>();
    params.put("name","access");
    params.put("postId", postId);
    params.put("userId", userId);

    boolean canSeePaid = postDao.checkUser(params);

    if (canSeePaid){

    }else {

    }

    return postDao.findByIdInContent(postId);
  }
}