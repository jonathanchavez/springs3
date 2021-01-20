package com.jonathan.springs3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;


@Service
public class FileUploadService {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${bucketName}")
    private String bucketName;

    public void uploadFile(final MultipartFile multipartFile){

        final File file = convertMultiPartFiletoFile(multipartFile);
        uploadFileToS3Bucket(bucketName, file);
        file.delete();
    }

    public File convertMultiPartFiletoFile(final MultipartFile multipartFile){
        File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    private void uploadFileToS3Bucket(final String bucketName, final File file) {
        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        amazonS3.putObject(putObjectRequest);
    }



//    Saving to local

//    adds the words uploaded before the file name
    private String uploadFolderPath = "uploaded_";
    public void uploadToLocal(MultipartFile file){
        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(uploadFolderPath + file.getOriginalFilename());
//            write to path
            Files.write(path,data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
