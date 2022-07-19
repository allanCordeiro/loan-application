package com.allancordeiro.creditanalysis.utils;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Component
public class EnvProperties {

    private Properties property;
    private String rootPath;

    public EnvProperties() {
        rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try(InputStream input = new FileInputStream( rootPath + "application.properties")) {
            this.property = new Properties();
            this.property.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getProperty(String property) {
        return this.property.getProperty(property);
    }
}
