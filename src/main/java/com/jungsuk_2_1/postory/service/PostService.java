package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.*;
import com.jungsuk_2_1.postory.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
//@Transactional
public class PostService {
  private final PostDao postDao;
  private final SeriesDao seriesDao;
  private final PostTagDao postTagDao;
  private final FileDao fileDao;

  private final UserDao userDao;

  private final PostLikeDao postLikeDao;

  private PostServiceDetail postServiceDetail;
  private PostService self;

  @Autowired
  PostService(PostDao postDao, SeriesDao seriesDao, PostTagDao postTagDao, FileDao fileDao, UserDao userDao, PostLikeDao postLikeDao, PostServiceDetail postServiceDetail, @Lazy PostService self) {
    this.postDao = postDao;
    this.seriesDao = seriesDao;
    this.postTagDao = postTagDao;
    this.fileDao = fileDao;
    this.userDao = userDao;
    this.postLikeDao = postLikeDao;
    this.postServiceDetail = postServiceDetail;
    this.self = self;
  }
  public PostDto createPost (PostDto postDto){

    postServiceDetail.setPostIds(postDto);
    postServiceDetail.create(postDto);
    postServiceDetail.updateBefoePost(postDto.getBefPostId(), postDto.getPostId());
    postServiceDetail.createPostTag(postDto.getPostId(), postDto.getPostType());
    postServiceDetail.createFile(postDto);

    PostDto dto = postDao.findById(postDto.getPostId());

    return dto;
  }

  public List<ChannelPostDto> getPostsByChnlUri (String chnlUri,String postType,int page, String orderMethod,int pageSize){
    if(postType.equals("webtoon")){
      postType = "웹툰";
    } else if(postType.equals("webnovel")){
      postType = "웹소설";
    }

    Map<String, Object> params = new HashMap<>();
    params.put("name", "channelPosts");
    params.put("chnlUri", chnlUri);
    params.put("pageSize", pageSize);
    params.put("offset", (page - 1) * pageSize);
    params.put("orderMethod", orderMethod);
    params.put("postType", postType);


    return postDao.getPostsByChnlUri(params);
  }

  public StudioPostDto getPostInStudio (String chnlUri){

    return postDao.findInStudioByChnlUri(chnlUri);
  }
  private Integer getMaxPostIdInSer (String chnlUri, Integer serId){
    Map<String, Object> params = new HashMap<>();
    params.put("chnlUri", chnlUri);
    params.put("serId", serId);

    if (serId == null){
      Integer maxId = postDao.findInNonSeries(params);
      return maxId;
    } else {
      Integer maxId = postDao.findInSeries(params);
      return maxId;
    }
}

  public PostDto updatePost (String userId, Integer postId, PostDto postDto) {


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
    params.put("postContent", postDto.getPostContent());

    System.out.println("postDto.getPostContent() = " +postDto.getPostContent());

    postDao.updatePost(params);

    fileDao.deleteByPostId(postId);

    List<String> urls = postDto.getImageUrls();
    System.out.println("200 urls = " + urls);
    String fileExtns;

    for (String filePath : urls) {
      fileExtns = filePath.substring(filePath.lastIndexOf(".") + 1); // ex) jpg
      filePath = filePath.substring(0, filePath.lastIndexOf(".")); // ex) 파일


      FileDto file = FileDto.builder().filePath(filePath).fileExtns(fileExtns).postId(postId).build();
      System.out.println("file = " + file);
      try {
        fileDao.save(file);
      } catch (Exception e) {
        e.printStackTrace();  // or use logger
      }
    }

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

  public PostViewDto readPostById(String userId, Integer postId) {
    postDao.increaseViewCount(postId);
    List<FileDto> files = fileDao.getFilesByPostId(postId);

    PostViewDto dto = postDao.findByIdInContent(postId);
    List<String> urls = new ArrayList<>();

    for(FileDto file : files) {
      String url = file.getFilePath()+"."+file.getFileExtns();
      urls.add(url);
    }
    dto.setImageUrls(urls);
    System.out.println("dto = " + dto);

    return dto;
  }

  public ChannelUserDto getUserByPostId(Integer postId) {
    return userDao.findByPostId(postId);
  }

  public ChannelSimpleDto getChannelByPostId(Integer postId){
    return postDao.findChannelByPostId(postId);
  }

  public boolean checkLike(Integer postId, String nic) {
    Map<String,Object> params = new HashMap<>();
    params.put("postId", postId);
    params.put("nic", nic);
    return postLikeDao.isLiked(params);
  }
}