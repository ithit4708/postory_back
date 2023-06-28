package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.ChannelSeriesDto;
import com.jungsuk_2_1.postory.dto.SeriesDto;
import com.jungsuk_2_1.postory.dto.StudioSeriesDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SeriesDao {
    public List<ChannelSeriesDto> findByChnlUri(Map<String, Object> params);
    void createSeries(Map<String, Object> params);
    int findLastId();
    StudioSeriesDto findInStudioByChnlUri(String chnlUri);
}
