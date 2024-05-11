package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

@Service
public class FileService implements IStorageService {
    private final Path storageFolder = Paths.get("uploads");
    private final Logger logger = Logger.getLogger(FileService.class.getName());
    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        try
        {
            Files.createDirectories(this.storageFolder);
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Could not create directory to store files", exception);
        }
    }

    private String getFileType(MultipartFile fileName)
    {
        String FileExtension= FilenameUtils.getExtension(fileName.getOriginalFilename());
        return FileExtension.toLowerCase();
    }
    @Override
    public boolean storeFile(MultipartFile file, User user) throws IOException {

        Integer userId = user.getUserId();
        File file1 = fileRepository.getFile(file.getOriginalFilename(), userId);
        logger.info("file1: " + file1);
        if (file1 != null) {
            return false;
        }
        byte[] fileData = file.getBytes();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        String fileName = file.getOriginalFilename();
        fileRepository.insert(new File(null, fileName, contentType, fileSize, userId, fileData));
        return true;
    };
    public List<File> getAllFiles(Integer userId) {
        return fileRepository.getAllFiles(userId);
    }
    public void deleteFiles(Integer fileId) {
        fileRepository.delete(fileId);
    }

    public File getFileById(Integer fileId) {
        return fileRepository.getFileById(fileId);
    }
}
