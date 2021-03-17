package com.monaim.storageservice.Configurations;

public class ControllersEndpoints {
    public static class PhotoControllerEndpoints {
        public static final String BASE = "/photos";
        public static final String RETRIEVE = "/{id}";
        public static final String UPLOAD = "/upload";
        public static final String DISPLAY = "/display/{filename}";
    }
    public static String setParam(String endpoint, String paramToReplace, String param) {
        return endpoint.replaceAll("\\{" + paramToReplace + "}", param);
    }
}
