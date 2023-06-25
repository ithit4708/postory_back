package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.SeriesDao;
import com.jungsuk_2_1.postory.dto.ChannelSeriesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeriesService {

    private final SeriesDao seriesDao;

    @Autowired
    public SeriesService(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }

    public List<ChannelSeriesDto> getSeriesByChnlUri(String chnlUri, int page, String orderMethod, int pageSize) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", "channelSeries");
        params.put("chnlUri", chnlUri);
        params.put("pageSize", pageSize);
        params.put("offset", (page - 1) * pageSize);
        params.put("orderMethod", orderMethod);

        return seriesDao.findByChnlUri(params);
    }
}
