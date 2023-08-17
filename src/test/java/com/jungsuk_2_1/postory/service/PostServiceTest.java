package com.jungsuk_2_1.postory.service;

//import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    PostService postService;

    String userId = "65b36830defd48b094f34c3a126f5ee7";
    PostDto postDto = PostDto.builder().chnlUri("buksan").postType("웹툰").postTtl("포스트 생성 테스트")
            .postSbTtl("포스트 생성 테스트").postContent("").postThumnPath("").userId(userId).nowPostStusChgrId(userId).build();

    @Test
    public void createPostTest() {

        PostDto result = postService.createPost(postDto);

        assertNotNull(result, "결과가 null입니다.");
        assertEquals(postDto.getPostTtl(), result.getPostTtl(), "포스트 제목이 일치하지 않습니다."); // 입력한 제목과 반환된 결과의 제목이 일치하는지 확인
    }
//    @Test
//    public void getMaxPOstIdInSerTest() {

//        Integer maxPostId = postService.getMaxPostIdInSer(postDto.getChnlUri(),postDto.getSerId());
//        System.out.println("maxPostId = " + maxPostId);
//        assertNotNull(maxPostId, "결과가 null입니다.");
//    }

}