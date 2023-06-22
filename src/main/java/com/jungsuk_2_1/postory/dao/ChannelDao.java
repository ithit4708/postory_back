package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChannelDao {
    List<ChannelDto> getChannels();
    void save(ChannelDto ChannelDto);
    ChannelDto findById(int id);
    int countUserChannels(String crtId);
    int findLastId();
    void delete(ChannelDto ChannelDto);
    List<ChannelDto> findByUserId(String id);
    boolean doesUriExist(String chnlUri);

    List<ChannelDto> findByUserNic(String nig);

    List<ChannelDto> findByChnlUri(String chnlUri);

    void update(ChannelDto channelDto);
}
