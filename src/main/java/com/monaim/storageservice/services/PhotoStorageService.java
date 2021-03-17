package com.monaim.storageservice.services;

import com.monaim.storageservice.exceptions.EntityNotFoundException;
import com.monaim.storageservice.models.BaseFile;
import com.monaim.storageservice.models.BaseFileDto;
import com.monaim.storageservice.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
public class PhotoStorageService {

    private final FileRepository fileRepository;
    private final FileStorageServiceImpl fileStorageService;

    public PhotoStorageService(FileStorageServiceImpl fileStorageService, FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.fileStorageService = fileStorageService;
    }
    @Value("${files-storage.directories.photos}")
    private String photoStorageDir;
    private Path dirPath;

    @PostConstruct
    public void init() throws IOException {
        dirPath = Paths.get(System.getProperty("user.home"), photoStorageDir);
        Files.createDirectories(dirPath);
    }

    public Resource load(String filename) throws MalformedURLException {
        fileRepository.findByFilename(filename).orElseThrow(() -> {
            throw new EntityNotFoundException("no such photo with that name");
        });
        Path file = dirPath.resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }


    public BaseFileDto.Response retrieve(Long id) {
        BaseFile photoFile = fileRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("no such photo with that id");
        });
        return BaseFileDto.fromEntity(photoFile);
    }

    public BaseFileDto.Response save(BaseFileDto.Request file) {
        BaseFile baseFile = fileStorageService.save(file.getFile(), dirPath.toString());
        return BaseFileDto.fromEntity(fileRepository.save(baseFile));
    }
}
