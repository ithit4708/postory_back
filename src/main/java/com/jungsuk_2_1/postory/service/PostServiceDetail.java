package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.FileDao;
import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dao.PostTagDao;
import com.jungsuk_2_1.postory.dto.FileDto;
import com.jungsuk_2_1.postory.dto.PostDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Getter
public class PostServiceDetail {
    private PostDao postDao;
    private PostTagDao postTagDao;
    private FileDao fileDao;
    private Integer maxPostIdInSer;
    @Autowired
    PostServiceDetail(PostDao postDao, PostTagDao postTagDao, FileDao fileDao){
        this.postDao = postDao;
        this.postTagDao = postTagDao;
        this.fileDao = fileDao;
    }

    public void setMaxPostIdInSer(String chnlUri ,Integer serId) {
        this.maxPostIdInSer = getMaxPostIdInSer(chnlUri, serId);
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

    public void setPostIds(PostDto postDto){
        Integer newPostId = postDao.findLastId() + 1;
        setMaxPostIdInSer(postDto.getChnlUri(), postDto.getSerId());
        Integer befPostId = maxPostIdInSer;

        postDto.setUserId(postDto.getUserId());
        postDto.setBefPostId(befPostId);
        postDto.setPostId(newPostId);
        postDto.setNextPostId(null);
    }

    public void create(PostDto postDto){

        Map<String, Object> params = new HashMap<>();
        params.put("name", "createPost");
        params.put("postId", postDto.getPostId());
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
//    params.put("basicFontCdNm", postDto.getBasicFontCdNm());
//    params.put("basicParagAlgnCdNm", postDto.getBasicParagAlgnCdNm());
        params.put("basicFontCdNm", "고딕체");
        params.put("basicParagAlgnCdNm", "가운데정렬");
        params.put("itdYn", postDto.getItdYn());
        params.put("paragGapMargYn", postDto.getParagGapMargYn());
        params.put("nowPostStusCdNm", postDto.getNowPostStusCdNm());
        params.put("nowPostStusCdNm", "최초발행");
        params.put("nowPostStusChgrId", postDto.getUserId());
        params.put("befPostId", postDto.getBefPostId());
        params.put("nextPostId", null);
        params.put("postContent", postDto.getPostContent());
        postDao.createPost(params);
    }

    public void updateBefoePost(Integer befPostId, Integer postId){
        System.out.println("befPostId = " + befPostId);
        System.out.println("postId = "+ postId);

        Map<String, Object> args = new HashMap<>();
        args.put("name","updateBefNestPostId");
        args.put("postId", befPostId);
        args.put("nextPostId", postId);
        postDao.updateNextPostId(args);
    }

    public void createPostTag(Integer newPostId, String postType){
        Map<String, Object> data = new HashMap<>();
        data.put("name","createPostTag");
        data.put("postId",newPostId);
        data.put("postType",postType);
        postTagDao.createPostTag(data);
    }

    public void createFile(PostDto postDto) {

        if(postDto.getImageUrls() == null || postDto.getImageUrls().size() == 0) return;
        List<String> urls = postDto.getImageUrls();
        String fileExtns;

        for(String filePath: urls) {
            fileExtns = filePath.substring(filePath.lastIndexOf(".") + 1); // ex) jpg
            filePath = filePath.substring(0, filePath.lastIndexOf(".")); // ex) 파일

            FileDto file = FileDto.builder().filePath(filePath).fileExtns(fileExtns).postId(postDto.getPostId()).build();
            try {
                fileDao.save(file);
            } catch (Exception e) {
                e.printStackTrace();  // or use logger
            }
        }
    }
}
