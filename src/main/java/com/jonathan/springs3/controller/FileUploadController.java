package com.jonathan.springs3.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.jonathan.springs3.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/")
public class FileUploadController {

//    service class
    @Autowired
    private FileUploadService service;




    @PostMapping("upload/s3")
    public ResponseEntity<String> uploadFilee(@RequestParam("file")final MultipartFile multipartFile) {
        service.uploadFile(multipartFile);
        String reponse = "upload";
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }


    @Autowired
    private FileUploadService fileUploadService;



//    saving the files locally
    @PostMapping("upload/local")
    public void uploadLocal(@RequestParam("file")MultipartFile multipartFile){

        fileUploadService.uploadToLocal(multipartFile);

    }

    public void uploadDb(@RequestParam("file")MultipartFile multipartFile){

    }




}
