package com.jungsuk_2_1.postory.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.jungsuk_2_1.postory.dto.FileDto;
import com.jungsuk_2_1.postory.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//import com.spring.multipart.dto.FoodDto;

@RestController
@RequestMapping("/file")
public class FileController {
    private FileService fileService;

    @Autowired
    FileController(FileService fileService){
        this.fileService = fileService;
    }
    @PostMapping("/uploadFiles")
    public ResponseEntity<Object> uploadFiles(MultipartFile[] multipartFiles) {
        String UPLOAD_PATH = "/Users/ho/Workspace/sanwangjapki/postory_front/public/images";

        List<Object> urls = new ArrayList<>();
        List<FileDto> fileList = new ArrayList<>();

        try {
            for (MultipartFile file : multipartFiles) {

                String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
                String originName = file.getOriginalFilename(); // ex) 파일.jpg
                String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
                originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
                long fileSize = file.getSize(); // 파일 사이즈

                File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
                if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
                    fileSave.mkdirs();
                }

                file.transferTo(fileSave); // fileSave의 형태로 파일 저장
                urls.add(fileId + "."+fileExtension);

                System.out.println("fileId= " + fileId);
                System.out.println("originName= " + originName);
                System.out.println("fileExtension= " + fileExtension);
                System.out.println("fileSize= " + fileSize);
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Fail");
        }
        Map<String,Object> data = new HashMap<>();

        data.put("urls", urls);

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/uploadThumn")
    public ResponseEntity<Object> uploadThumn(MultipartFile file) {
        String UPLOAD_PATH = "/Users/ho/Workspace/sanwangjapki/postory_front/public/images";

        String url;

        System.out.println("file = " + file);

        try {

            String fileId = (new Date().getTime()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()); // 현재 날짜와 랜덤 정수값으로 새로운 파일명 만들기
            String originName = file.getOriginalFilename(); // ex) 파일.jpg
            String fileExtension = originName.substring(originName.lastIndexOf(".") + 1); // ex) jpg
            originName = originName.substring(0, originName.lastIndexOf(".")); // ex) 파일
            long fileSize = file.getSize(); // 파일 사이즈

            File fileSave = new File(UPLOAD_PATH, fileId + "." + fileExtension); // ex) fileId.jpg
            if(!fileSave.exists()) { // 폴더가 없을 경우 폴더 만들기
                fileSave.mkdirs();
            }

            file.transferTo(fileSave); // fileSave의 형태로 파일 저장
            url = fileId + "." + fileExtension;
//
            System.out.println("fileId= " + fileId);
            System.out.println("originName= " + originName);
            System.out.println("fileExtension= " + fileExtension);
            System.out.println("fileSize= " + fileSize);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Fail");
        }
        Map<String,Object> data = new HashMap<>();

        data.put("url", url);

        return ResponseEntity.ok().body(data);
    }
}