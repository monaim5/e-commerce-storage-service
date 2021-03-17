package com.monaim.storageservice.Configurations;

import com.monaim.storageservice.exceptions.UnknownException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;

@Getter
public class ApiBaseUrl {

    @Value("${server.port}")
    private int port;
    private URL url;

    @PostConstruct
    public void init() throws MalformedURLException {

        String protocol = "http";
        String host = "localhost";
        this.url = new URL(protocol, host, port, "");
    }

    public URL join(String path) {
        try {
            return new URL(this.url, path);
        } catch (MalformedURLException e) {
            throw new UnknownException("An male formed url Exception has thrown");
        }
    }
}
