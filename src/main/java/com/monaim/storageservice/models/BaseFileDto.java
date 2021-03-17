package com.monaim.storageservice.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


public class BaseFileDto {



    @Data
    @Builder
    @ToString
    public static class Response {
        private Long id;
        private String url;
        private String filename;
    }

    @Builder
    @Data
    public static class Request {
        private MultipartFile file;
        public Request(MultipartFile file) {
            this.file = file;
        }
    }

    public static Response fromEntity(BaseFile baseFile) {
        return Response.builder()
                .id(baseFile.getId())
                .filename(baseFile.getFilename())
                .build();
    }
}
