package com.cdutcm.register.service.impl;

import com.cdutcm.register.enums.ResultEnum;
import com.cdutcm.register.exception.RegisterException;
import com.cdutcm.register.properties.RegisterProperties;
import com.cdutcm.register.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2019/2/15 18:33 星期五
 * Description:
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    private final String rootLocation;

    @Autowired
    public StorageServiceImpl(RegisterProperties registerProperties) {
        this.rootLocation = registerProperties.getStorage().getLocation();
    }

    @Override
    public void init() {
    }

    @Override
    public void store(MultipartFile file) {
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public URL load(String filename) {
        return this.getClass().getResource(rootLocation + filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            URL url = load(filename);
            Resource resource = new UrlResource(url);
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.error("【文件加载】文件不存在，filename = {}", filename);
                throw new RegisterException(ResultEnum.FILE_LOAD_ERROR);
            }
        } catch (Exception e) {
            log.error("【文件加载】文件不存在，filename = {}", filename);
            throw new RegisterException(ResultEnum.FILE_LOAD_ERROR);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(new File(""));
    }
}
