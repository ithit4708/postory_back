package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dao.FileDao;
import com.jungsuk_2_1.postory.dto.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    private FileDao fileDao;

    @Autowired
    FileService(FileDao fileDao){
        this.fileDao =fileDao;
    }

    public List<FileDto> saveAll(List<FileDto> files){

        long startFileId = fileDao.findLastId();

        System.out.println("startFileId = " + startFileId);

        int row = fileDao.saveAll(files);

        System.out.println("row = " + row);

        long endFileId = fileDao.findLastId();

        System.out.println("endFileId:w = " + endFileId);

        Map<String, Object> params = new HashMap<>();
        params.put("startFileId", startFileId);
        params.put("endFileId", endFileId);
        List<FileDto> fileDtos = fileDao.getFilesByIdRange(params);

        return fileDtos;
    }

    public FileDto save(FileDto fileDto){
        fileDao.save(fileDto);;

        return fileDao.findById(fileDto.getFileId());
    }


}
