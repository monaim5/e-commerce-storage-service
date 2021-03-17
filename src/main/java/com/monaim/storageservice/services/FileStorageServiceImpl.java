package com.monaim.storageservice.services;

import com.monaim.storageservice.exceptions.UnknownException;
import com.monaim.storageservice.models.BaseFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl {

    public BaseFile save(MultipartFile file, String directory) {
        // prepare file metadata
        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + "." + ext;
        Path path = Paths.get(directory, filename);

        // save file
        try {
            file.transferTo(new File(path.toString()));
        } catch (IOException e) {
            throw new UnknownException("An IO exception has occurred");
        }

        // return saved file
        return BaseFile.builder()
                .filename(filename)
                .path(path.toString())
                .ContentType(file.getContentType())
                .size(file.getSize())
                .build();
    }
}
