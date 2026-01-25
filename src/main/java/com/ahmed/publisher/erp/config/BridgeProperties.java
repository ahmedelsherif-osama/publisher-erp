package com.ahmed.publisher.erp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bridge")
public class BridgeProperties {

    /**
     * Base URL of the Bridge API
     */
    private String url;

    // getter & setter
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
