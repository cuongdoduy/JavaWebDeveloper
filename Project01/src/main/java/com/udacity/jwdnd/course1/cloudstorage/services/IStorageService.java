package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStorageService {
    public boolean storeFile(MultipartFile file, User user) throws IOException;
    public void deleteFiles(Integer fileId);

    public List<File> getAllFiles(Integer userId);


}
