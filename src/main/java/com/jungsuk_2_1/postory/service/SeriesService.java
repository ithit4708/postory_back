package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.SeriesDao;
import com.jungsuk_2_1.postory.dto.ChannelSeriesDto;
import com.jungsuk_2_1.postory.dto.SeriesDto;
import com.jungsuk_2_1.postory.dto.StudioSeriesDto;
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

    public StudioSeriesDto createSeries(SeriesDto seriesDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("serId", seriesDto.getSerId());
        params.put("serThumnPath", seriesDto.getSerThumnPath());
        params.put("serTtl", seriesDto.getSerTtl());
        params.put("serDesc", seriesDto.getSerDesc());
        params.put("serOpenDtm", seriesDto.getSerOpenDtm());
        params.put("chnlDsgntSerOdr", seriesDto.getChnlDsgntSerOdr());
        params.put("recenPblcPostId", seriesDto.getRecenPblcPostId());
        params.put("chnlId", seriesDto.getChnlId());
        params.put("chnlUri",seriesDto.getChnlUri());
        params.put("serStusCd", seriesDto.getSerStusCd());
        params.put("serStusChgrId", seriesDto.getSerStusChgrId());

        seriesDao.createSeries(params);

        return getSeriesInStudio(seriesDto.getChnlUri());
    }

    public StudioSeriesDto getSeriesInStudio(String chnlUri){
        return seriesDao.findInStudioByChnlUri(chnlUri);
    };

}
