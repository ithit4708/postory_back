package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.LikeDao;
import com.jungsuk_2_1.postory.dto.SubscriptionPostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LikeServiceImpl implements LikeService {
    LikeDao likeDao;

    LikeServiceImpl(LikeDao likeDao) {
        this.likeDao = likeDao;
    }

    @Override
    public void addToLikeList(Map<String, Object> likeInfoMap) {
        //post_like 테이블에 해당 포스트가 추가 됨
        likeDao.insertLike(likeInfoMap);
        //post 테이블에 해당 포스트의 like 수가 count 되어 update (+)
        likeDao.updateLikeToPost(likeInfoMap.get("postId"));
    }

    @Override
    public void removeFromLikeList(Map<String, Object> likeCancleInfoMap) {
        //post_like 테이블에 해당 포스트가 제거 됨
        likeDao.deleteLike(likeCancleInfoMap);
        //post 테이블에 해당 포스트의 like 수가 count 되어 update (-)
        likeDao.updateLikeToPost(likeCancleInfoMap.get("postId"));
    }

    @Override
    public List<SubscriptionPostDto> getLikePostList(String userId) {
        return likeDao.selectLikePost(userId);
    }
}
