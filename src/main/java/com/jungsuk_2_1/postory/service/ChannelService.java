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

    public List<ChannelDto> getChannels() {
        return channelDao.getChannels();
    }

    public List<ChannelDto> createChannel(ChannelDto channel) {
        validate(channel);

        int newChnlId = channelDao.findLastId() + 1;
        channel.setChnlId(newChnlId);

        channelDao.save(channel);





        return channelDao.findByUserId(channel.getCrtId());
    }

    public List<ChannelDto> retrieve(final String userId){
        return channelDao.findByUserId(userId);
    }


    public List<ChannelDto> update(final ChannelDto dto) {
        validate(dto);


        final Optional<ChannelDto> original = Optional.ofNullable(channelDao.findById(dto.getChnlId()));

        original.ifPresent(channel -> {
            channel.setChnlTtl(dto.getChnlTtl());


            channelDao.save(channel);
        });

        return retrieve(dto.getCrtId());

    }

    public List<ChannelDto> delete(final ChannelDto dto) {
        validate(dto);

        try {

            channelDao.delete(dto);

        } catch (Exception e) {

            log.error("error deleting dto ", dto.getChnlId(), e);
            throw new RuntimeException("error deleting dto " + dto.getCrtId());

        }
        return retrieve(dto.getCrtId());
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






}
