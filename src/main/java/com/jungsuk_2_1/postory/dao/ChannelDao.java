package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ChannelDto;
import com.jungsuk_2_1.postory.dto.ChannelSimpleDto;
import com.jungsuk_2_1.postory.dto.OnlyIdDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChannelDao {
    void save(ChannelDto ChannelDto);
    ChannelDto findById(int id);
    int countUserChannels(String crtId);
    int findLastId();
    void delete(ChannelDto ChannelDto);

    void deleteChannel(String chnlUri);
    List<ChannelDto> findByUserId(String id);

    boolean doesUriExist(String chnlUri);

    List<ChannelDto> findByUserNic(String nig);

    ChannelDto findByChnlUri(String chnlUri);

    void update(ChannelDto channelDto);

    ChannelSimpleDto findIdByChnlUri(String chnlUri);
}
