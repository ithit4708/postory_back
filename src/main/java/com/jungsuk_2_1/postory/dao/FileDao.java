package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.FileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileDao {
    int saveAll(List<FileDto> files);

    Integer findLastId();

    List<FileDto> getFilesByIdRange(Map<String, Object> params);

    void save(FileDto fileDto);

    FileDto findById(Integer fileId);

    List<FileDto> getFilesByPostId(Integer postId);

    void deleteByPostId(Integer postId);
}
