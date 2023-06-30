package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.ChannelDao;
import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dao.SeriesDao;
import com.jungsuk_2_1.postory.dao.UserDao;
import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.ChannelSimpleDto;
import com.jungsuk_2_1.postory.dto.ChannelUserDto;
import com.jungsuk_2_1.postory.dto.OnlyIdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ChannelService {
    private final ChannelDao channelDao;
    private final UserDao userDao;
    private final PostService postService;
    private final SeriesService seriesService;
    private final SeriesDao seriesDao;
    private final PostDao postDao;

    @Autowired
    public ChannelService(ChannelDao channelDao, UserDao userDao, PostService postService, SeriesService seriesService, SeriesDao seriesDao, PostDao postDao){
        this.channelDao = channelDao;
        this.userDao = userDao;
        this.postService = postService;
        this.seriesService = seriesService;
        this.seriesDao = seriesDao;
        this.postDao = postDao;
    }

    public ChannelUserDto getUserByChannelUri(final String channelUri){
        return userDao.findByChnlUri(channelUri);
    }


    public List<ChannelDto> createChannel(String userId, ChannelDto channel) {
//      위 부분을 책에서는 Controller에서 했다. 왜일까? 집에서 책 보며 체크.
        channel.setCrtId(userId);
        channel.setChnlStusChgrId(userId);
//        uri중복체크.
        // uri 중복체크를 밖에서 해야할까, 아니면 함수 내에서 해야할까.
        //밖에서 하는 것이 성능이 더 좋을 것 같다. 하지만 포스타입은 안에서 함.
        checkDuplicate(channel);

//       채널이 3개면 더이상 못 만듦.

        int count = channelDao.countUserChannels(channel.getCrtId());

        if (count > 3) {
            log.warn("Channel cannot be more than 3");
            throw new RuntimeException("Channel cannot be more than 3");
        } else {
            int newChnlId = channelDao.findLastId() + 1;
            channel.setChnlId(newChnlId);
            channelDao.save(channel);
        }

        return channelDao.findByUserId(channel.getCrtId());
    }

    public ChannelDto retrieve(final String chnlUri){
        return channelDao.findByChnlUri(chnlUri);
    }
    public ChannelDto update(String userId ,final ChannelDto dto) {
        dto.setCrtId(userId);

        final Optional<ChannelDto> original = Optional.ofNullable(channelDao.findById(dto.getChnlId()));
        original.ifPresent(channel -> {
            if (!channel.getChnlUri().equals(dto.getChnlUri())) {checkDuplicate(dto);}
            channel.setChnlUri(dto.getChnlUri());
            channel.setChnlTtl(dto.getChnlTtl());
            channel.setChnlImgPath(dto.getChnlImgPath());
            channel.setChnlIntro(dto.getChnlIntro());
            channel.setChnlSbTtl(dto.getChnlSbTtl());
            channelDao.update(channel);
        });
        return channelDao.findByChnlUri(dto.getChnlUri());
    }

    public List<ChannelDto> delete(final String userId, final String chnlUri) {

        ChannelSimpleDto channel = channelDao.findIdByChnlUri(chnlUri);
        Integer chnlId = channel.getChnlId();
//        나눠서 처리해야 없어서 못없애는 것과 있는데 못없애는 걸 구분할 수 있다.
        try {
//            1. 채널아이디와 일치하는 채널을 가져온다.
            // 2. 가져온 채널 crtId와 매개변수 userId가 같은지 확인한다.
//                dtos.get(0).setChnlStusCd("ST00220");
//                이걸 mybatis영역에서 하나, service영역에서 하나.
//            mapper에서 처리하는 게 더 안전해 보임.

            List<OnlyIdDto> postIds = postDao.findIdByChnlId(chnlId);
            for (OnlyIdDto postId : postIds){
                postService.deletePost(postId.getId());
            }
            seriesDao.deleteSeriesByChnlId(chnlId);
            channelDao.deleteChannel(chnlUri);
        } catch (Exception e) {
            log.error("error deleting dto {}", chnlId, e);
            throw new RuntimeException("error deleting channel " + chnlId);
        }
        return channelDao.findByUserId(channel.getCrtId());
    }

    private void checkDuplicate(final ChannelDto dto){
        boolean exist = channelDao.doesUriExist(dto.getChnlUri());

        if (exist){
            log.warn("Uri already exists");
            throw new RuntimeException("Uri already exists");
        }
    }
}
