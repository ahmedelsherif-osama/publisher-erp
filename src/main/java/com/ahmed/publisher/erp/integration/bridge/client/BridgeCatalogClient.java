package com.ahmed.publisher.erp.integration.bridge.client;

import com.ahmed.publisher.erp.integration.bridge.dto.BridgePublicationRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("bridgeRestTemplate")
public class BridgeCatalogClient {

    private final RestTemplate restTemplate;

    public BridgeCatalogClient(@Qualifier("bridgeRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void pushPublications(BridgePublicationRequest publication) {
        System.out.println("ERP sending: " + publication);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<BridgePublicationRequest> entity = new HttpEntity<>(publication, headers);
            var response = restTemplate.postForEntity(
                    "http://localhost:8080/bridge/catalog/publications",
                    entity,
                    String.class
            );
            System.out.println("ERP got status: " + response.getStatusCode());
            System.out.println("ERP got body: " + response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
