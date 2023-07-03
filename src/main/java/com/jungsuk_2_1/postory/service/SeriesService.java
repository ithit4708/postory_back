package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.PostDao;
import com.jungsuk_2_1.postory.dao.SeriesDao;
import com.jungsuk_2_1.postory.dto.ChannelSeriesDto;
import com.jungsuk_2_1.postory.dto.OnlyIdDto;
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
    private final PostDao postDao;

    private final PostService postService;

    @Autowired
    public SeriesService(SeriesDao seriesDao, PostDao postDao, PostService postService) {
        this.seriesDao = seriesDao;
        this.postDao = postDao;
        this.postService = postService;
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

    public StudioSeriesDto createSeries(String userId,SeriesDto seriesDto) {

        Integer newSerId = seriesDao.findLastId() + 1;

        Map<String, Object> params = new HashMap<>();
        params.put("serId", newSerId);
        params.put("serThumnPath", seriesDto.getSerThumnPath());
        params.put("serTtl", seriesDto.getSerTtl());
        params.put("serDesc", seriesDto.getSerDesc());
        params.put("serOpenDtm", seriesDto.getSerOpenDtm());
        params.put("chnlDsgntSerOdr", seriesDto.getChnlDsgntSerOdr());
        params.put("recenPblcPostId", seriesDto.getRecenPblcPostId());
        params.put("chnlId", seriesDto.getChnlId());
        params.put("chnlUri",seriesDto.getChnlUri());
        params.put("serStusChgrId",userId);
        params.put("serStusCdNm", seriesDto.getSerStusCdNm());

        seriesDao.createSeries(params);
        seriesDao.findById(newSerId);

        return seriesDao.findById(newSerId);
    }

    public StudioSeriesDto getSeriesInStudio(String chnlUri){
        return seriesDao.findInStudioByChnlUri(chnlUri);
    };

    public StudioSeriesDto updateSeries(String userId, Integer seriesId, SeriesDto seriesDto) {

        Map<String, Object> params = new HashMap<>();
        params.put("serId", seriesId);
        params.put("serThumnPath", seriesDto.getSerThumnPath());
        params.put("serTtl", seriesDto.getSerTtl());
        params.put("serDesc", seriesDto.getSerDesc());
        params.put("chnlDsgntSerOdr", seriesDto.getChnlDsgntSerOdr());
        params.put("serStusChgrId",userId);
        params.put("serStusCdNm", seriesDto.getSerStusCdNm());

        seriesDao.updateSeries(params);

        return seriesDao.findById(seriesId);
    }

    public boolean deleteSeries(Integer serId) {
        List<OnlyIdDto> postIds = postDao.findIdBySerId(serId);


        for (OnlyIdDto postId : postIds){
            postService.deletePost(postId.getId());
        }
        seriesDao.deleteSeries(serId);
        return seriesDao.doesExist(serId);
    }

    public SeriesDto retrieveSeries(Integer serId, int page, String orderMethod, int pageSize) {

        Map<String, Object> params = new HashMap<>();
        params.put("name", "seriesPosts");
        params.put("serId", serId);
        params.put("pageSize", pageSize);
        params.put("offset", (page - 1) * pageSize);
        params.put("orderMethod", orderMethod);
        return seriesDao.findByIdInChnl(params);
    }
}
