package com.jonathan.springs3;

import com.jonathan.springs3.service.FileUploadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MyStepdefs extends SpringIntegrationTest{

    @Autowired
    private FileUploadService fileUploadService;

    MockMultipartFile file;

    File fil ;

    @Given("A multipart file with content of {string}")
    public void aMultipartFileWithContentOf(String content) {
        file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                content.getBytes()
        );
    }

    @io.cucumber.java.en.When("convert it to a file")
    public void convertItToAFile() {


        fil = fileUploadService.convertMultiPartFiletoFile(file);

    }
    @Then("we have a file with content of {string}")
    public void weHaveAFileWithContentOf(String body) throws IOException {
        Scanner scanner = new Scanner(Paths.get(String.valueOf(fil)), StandardCharsets.UTF_8.name());
        String content = scanner.useDelimiter("\\A").next();
        scanner.close();
//        checks to see if the content in mutlipart file and file are the same
        Assertions.assertEquals(content, body);

//        deletes the file afterwards
        fil.delete();
    }


    @When("save file to local")
    public void saveFileToLocal() {
        fileUploadService.uploadToLocal(file);
    }

    @Given("A multipart file with name {string}")
    public void aMultipartFileWithName(String name) {
        file = new MockMultipartFile(
                "file",
                name,
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }

    @Then("A file with the name should exist in the local directory that is name {string}")
    public void aFileWithTheNameShouldExistInTheLocalDirectoryThatIsName(String name) {
        File f = new File("/Users/chavezjl1/Desktop/uploaded_"+name);

        Assertions.assertTrue(f.exists());

    }
}























//    @io.cucumber.java.en.Given("A multipart file")
//    public void aMultipartFile() {
//                file
//                = new MockMultipartFile(
//                "file",
//                "hello.txt",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, World!".getBytes()
//        );
//    }

//    @io.cucumber.java.en.Then("we have a file")
//    public void weHaveAFile() throws IOException {
//
//
//        Scanner scanner = new Scanner(Paths.get(String.valueOf(fil)), StandardCharsets.UTF_8.name());
//        String content = scanner.useDelimiter("\\A").next();
//        scanner.close();
//        System.out.println(content);
//    }