package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.ChannelDao;
import com.jungsuk_2_1.postory.dto.ChannelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ChannelService {
    private final ChannelDao channelDao;

    @Autowired
    public ChannelService(ChannelDao channelDao){
        this.channelDao = channelDao;
    }


    public List<ChannelDto> createChannel(String userId, ChannelDto channel) {
//       채널이 40개면 더이상 못 만듦.

        channel.setCrtId(userId);
        channel.setChnlStusChgrId(userId);
        validate(channel);
//        uri중복체크.
        // uri 중복체크를 밖에서 해야할까, 아니면 함수 내에서 해야할까.
        //밖에서 하는 것이 성능이 더 좋을 것 같다. 하지만 포스타입은 안에서 함.

        checkDuplicate(channel);


        int count = channelDao.countUserChannels(channel.getCrtId());

        if (count > 40) {
            log.warn("Channel cannot be more than 40");
            throw new RuntimeException("Channel cannot be more than 40");
        } else {
            int newChnlId = channelDao.findLastId() + 1;
            channel.setChnlId(newChnlId);
            channelDao.save(channel);
        }

        return channelDao.findByUserId(channel.getCrtId());
    }

    public List<ChannelDto> retrieve(final String chnlUri){
        return channelDao.findByChnlUri(chnlUri);
    }

    public List<ChannelDto> update(String userId,final ChannelDto dto) {
        dto.setCrtId(userId);
        validate(dto);
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

        List<ChannelDto> dtos = channelDao.findByChnlUri(chnlUri);

        validate(dtos.get(0));
        try {
//            1. 채널아이디와 일치하는 채널을 가져온다.
            // 2. 가져온 채널 crtId와 매개변수 userId가 같은지 확인한다.
            if (dtos.get(0).getCrtId().equals(userId)){
                dtos.get(0).setChnlStusCd("ST00220");
//                이걸 mybatis영역에서 하나, service영역에서 하나.
                channelDao.delete(dtos.get(0));
            }
        } catch (Exception e) {
            log.error("error deleting dto ", dtos.get(0).getChnlId(), e);
            throw new RuntimeException("error deleting dto " + dtos.get(0).getCrtId());
        }
        return channelDao.findByUserId(dtos.get(0).getCrtId());
    }
    private void validate (final ChannelDto dto){
        if(dto == null ){
            log.warn("Dto cannot be null.");
            throw new RuntimeException("Dto cannot be null");
        }
        if(dto.getCrtId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown error");
        }
    }

    private void checkDuplicate(final ChannelDto dto){
        boolean exist = channelDao.doesUriExist(dto.getChnlUri());

        if (exist){
            log.warn("Uri already exists");
            throw new RuntimeException("Uri already exists");
        }
    }
}
