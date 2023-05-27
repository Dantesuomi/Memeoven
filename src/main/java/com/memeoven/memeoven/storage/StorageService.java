package com.memeoven.memeoven.storage;

import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    public void init();

    public String save(MultipartFile file);



}
