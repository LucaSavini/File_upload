package com.example.FileUpload.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName =newFileName + "." + extension;
        File finalFolder = new File(fileRepositoryFolder);
        System.out.println(finalFolder);
        if (!finalFolder.exists()) throw new IOException("Final Folder doesn't exists"+ fileRepositoryFolder);
        if (!finalFolder.isDirectory()) throw new IOException("Final Folder isn't a Directory");

        File finalDestination = new File(fileRepositoryFolder + "\\" + completeFileName);
        if (finalDestination.exists()) throw new IOException("File Conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

    public byte[] download(String fileName) throws IOException{
        File fileFromRepository = new File(fileRepositoryFolder+ "\\" + fileName);
        if (!fileFromRepository.exists()) throw new IOException("File does not exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }


    public void delete(String nameFile) {
        File fileFromRepository = new File(fileRepositoryFolder, nameFile);
        fileFromRepository.delete();
    }
}