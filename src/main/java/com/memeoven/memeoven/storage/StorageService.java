package com.memeoven.memeoven.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    public void init();

    public String save(MultipartFile file);

    public Resource load(String filename);


}
