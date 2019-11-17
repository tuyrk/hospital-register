package com.cdutcm.register.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 18:32 星期五
 * Description:
 */
public interface StorageService {
    void init();
    void store(MultipartFile file);
    Stream<Path> loadAll();
    URL load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();
}
