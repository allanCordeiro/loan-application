package com.allancordeiro.creditanalysis.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvProperties  {
    private Properties properties = new Properties();

    public EnvProperties() {}

    public String getProperty(String propertyKey) {
        try {
            this.PropertiesUtility();
            return this.properties.getProperty(propertyKey);
        } catch (IOException ioEx) {
            return "0";
        }
    }
    protected void PropertiesUtility() throws IOException {
        InputStream inputStream =
                getClass().getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);
    }

}
