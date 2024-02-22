package com.nextitproject.libraryapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


public class AppProperties {
    @Value("${library.file.path}")
    private String libraryFilePath;

    public String getLibraryFilePath() {
        return libraryFilePath;
    }
}