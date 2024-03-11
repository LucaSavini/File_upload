package com.example.FileUpload.dto;


import com.example.FileUpload.entities.User;
import lombok.Data;

@Data
public class DownloadPicture {

    private User user;
    private byte[] photo;

}