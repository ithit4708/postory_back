package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceDetailTest {

    @Autowired
    PostServiceDetail postServiceDetail;

    String userId = "65b36830defd48b094f34c3a126f5ee7";
    PostDto postDto = PostDto.builder().chnlUri("buksan").postType("웹툰").postTtl("포스트 생성 테스트")
            .postSbTtl("포스트 생성 테스트 영역").postContent("").postThumnPath("").userId(userId).nowPostStusChgrId(userId).build();

    @Autowired
    private PostDao postDao;
    @Test
    void setPostIds(){
        Integer befPostId = postDao.findLastId();
        Integer newPostId = befPostId + 1;
        postServiceDetail.setPostIds(postDto);

        assertEquals(postDto.getPostId(), newPostId, "Dto postId가 없데이트 안 됐어");
        assertEquals(postDto.getBefPostId(), befPostId, "Dto befPostId가 없데이트 안 됐어");
        assertNull(postDto.getNextPostId(), "Dto nextPostId가 없데이트 안 됐어");
    }
    @Test
    void create() {

        postDto.setPostTtl("create test");
        postServiceDetail.setPostIds(postDto);
        postServiceDetail.create(postDto);

        PostDto result = postDao.findById(postDto.getPostId());

        assertEquals(postDto.getPostTtl(),"create test","제목이 업데이트되지 않음");
        assertNotNull(result,"만든 포스트가 안 찾아져");
        assertEquals(postDto.getPostTtl(), result.getPostTtl(), "포스트 제목이 일치하지 않습니다."); // 입력한 제목과 반환된 결과의 제목이 일치하는지 확인
    }

    @Test
    void updateBefoePost() {
        postDto.setPostTtl("update before-post test");
        postServiceDetail.setPostIds(postDto);
        postServiceDetail.create(postDto);

        postServiceDetail.updateBefoePost(postDto.getBefPostId(), postDto.getPostId());
        PostDto updateBefore = postDao.findById(postDto.getBefPostId());
        PostDto result = postDao.findById(postDto.getPostId());
        assertEquals(postDto.getPostTtl(), result.getPostTtl(), "포스트 제목이 일치하지 않습니다."); // 입력한 제목과 반환된 결과의 제목이 일치하는지 확인
        assertEquals(postDto.getPostId(), updateBefore.getNextPostId(), "참조하는 포스트 번호가 옳바르지 않습니다."); // 입력한 제목과 반환된 결과의 제목이 일치하는지 확인
    }

    @Test
    void createPostTag() {
        postDto.setPostTtl("createPostTag test");
        postDto.setPostType("웹소설");
        postServiceDetail.setPostIds(postDto);
        postServiceDetail.create(postDto);
        postServiceDetail.updateBefoePost(postDto.getBefPostId(), postDto.getPostId());
        postServiceDetail.createPostTag(postDto.getPostId(), postDto.getPostType());
        PostDto result = postDao.findById(postDto.getPostId());

        assertEquals(postDto.getPostTtl(), result.getPostTtl(), "포스트 제목이 일치하지 않습니다."); // 입력한 제목과 반환된 결과의 제목이 일치하는지 확인
        assertEquals(result.getTagId(), 1,"태그가 안 생성됐어요.");
    }

    @Test
    void createFile() {
        postDto.setPostTtl("create file test");
        postServiceDetail.setPostIds(postDto);
        postServiceDetail.create(postDto);
        postServiceDetail.updateBefoePost(postDto.getPostId(), postDto.getPostId());
        postServiceDetail.createPostTag(postDto.getNextPostId(), postDto.getPostType());
        postServiceDetail.createFile(postDto);

        PostDto result = postDao.findById(postDto.getPostId());
        assertEquals(postDto.getPostTtl(), result.getPostTtl(), "포스트 제목이 일치하지 않습니다."); // 입력한 제목과 반환된 결과의 제목이 일치하는지 확인
    }
}