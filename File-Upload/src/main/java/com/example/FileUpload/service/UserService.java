package com.example.FileUpload.service;

import com.example.FileUpload.entities.User;
import com.example.FileUpload.repo.UserRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileService fileService;

    public List<User> list(){
        return userRepo.findAll();
    }

    public User showUser(Long id){
        return userRepo.findById(id).get();
    }

    public User createUser(User userOne) {
        return userRepo.saveAndFlush(userOne);
    }
    @SneakyThrows
    public User uploadPhoto(Long id, MultipartFile file) {
        User user = userRepo.findById(id).get();
        if (user.getFoto() != null) {
            fileService.delete(user.getFoto());
        }
        user.setFoto(fileService.upload(file));
        return userRepo.saveAndFlush(user);
    }
    @SneakyThrows
    public byte[] downloadPhoto(Long id, MultipartFile profilePic) {
        User user = userRepo.findById(id).get();
        return fileService.download(user.getFoto());

    }


    public void delete(Long id) {
        User user = userRepo.findById(id).get();
        if (user.getFoto() != null) {
            fileService.delete(user.getFoto());
        }
        userRepo.deleteById(id);
    }
}