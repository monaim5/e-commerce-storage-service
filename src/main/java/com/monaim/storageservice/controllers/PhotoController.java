package com.monaim.storageservice.controllers;

import com.monaim.storageservice.Configurations.ApiBaseUrl;
import com.monaim.storageservice.Configurations.ControllersEndpoints;
import com.monaim.storageservice.exceptions.EntityNotFoundException;
import com.monaim.storageservice.models.BaseFileDto;
import com.monaim.storageservice.services.PhotoStorageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static com.monaim.storageservice.Configurations.ControllersEndpoints.PhotoControllerEndpoints.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(BASE)
public class PhotoController {

    private final PhotoStorageService photoStorageService;
    private final ApiBaseUrl apiBaseUrl;

    @GetMapping
    public ResponseEntity<String> welcome() {
        return ResponseEntity.status(HttpStatus.OK).body("WELCOME IN FILE STORAGE SERVICE");
    }

    @GetMapping(path = RETRIEVE)
    public ResponseEntity<BaseFileDto.Response> retrieve(@PathVariable("id") Long id) throws IOException {
        BaseFileDto.Response photoDto = photoStorageService.retrieve(id);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).display(photoDto.getFilename()));
        photoDto.setUrl(link.toUri().toString());
        return ResponseEntity.status(HttpStatus.OK).body(photoDto);
    }

    @GetMapping(path = DISPLAY, produces = {MediaType.IMAGE_JPEG_VALUE})
    public byte[] display(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(photoStorageService.load(filename).getFile().toPath());
    }

    @CrossOrigin({"http://localhost:4200", "http://localhost:8082"})
    @PostMapping(path = UPLOAD, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<BaseFileDto.Response> upload(@ModelAttribute BaseFileDto.Request fileDto) {
        log.info("uploading ...");
        BaseFileDto.Response photoDto = photoStorageService.save(fileDto);
        URL displayUrl = apiBaseUrl.join(
                ControllersEndpoints.setParam(BASE + DISPLAY, "filename", photoDto.getFilename()));
        photoDto.setUrl(displayUrl.toString());
        log.info("upload end");
        return ResponseEntity.status(HttpStatus.CREATED).body(photoDto);
    }

}
