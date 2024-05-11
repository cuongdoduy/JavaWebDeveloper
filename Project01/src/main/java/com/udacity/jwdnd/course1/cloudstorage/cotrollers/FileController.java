package com.udacity.jwdnd.course1.cloudstorage.cotrollers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    private FileService fileService;
    @Autowired
    private UserService userService;


    public FileController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping("/upload")
    public String postUploadFile(@RequestParam("file") MultipartFile file, Authentication authentication)
    {
        String userName = (String) authentication.getPrincipal();
        User user = userService.getUser(userName);
        Boolean bool = false;
        try {
             bool = this.fileService.storeFile(file, user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bool?"redirect:/result?isSuccess=true":"redirect:/result?isSuccess=false&message=File already exists";

    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId)
    {
        this.fileService.deleteFiles(fileId);
        return "redirect:/home";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable Integer fileId) {
        File file = fileService.getFileById(fileId);

        if (file != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(file.getContenttype()));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getFilename()).build());

            return new ResponseEntity<>(file.getFiledata(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
