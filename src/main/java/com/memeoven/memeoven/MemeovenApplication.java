package com.memeoven.memeoven;

import com.memeoven.memeoven.meme.MemeFileService;
import com.memeoven.memeoven.storage.StorageService;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemeovenApplication implements CommandLineRunner {
    @Resource
    MemeFileService memeFileService;

    public static void main(String[] args) {
        SpringApplication.run(MemeovenApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        memeFileService.init();
    }
}
