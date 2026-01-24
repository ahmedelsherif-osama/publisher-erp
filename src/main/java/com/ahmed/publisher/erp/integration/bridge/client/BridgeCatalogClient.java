package com.ahmed.publisher.erp.integration.bridge.client;

import com.ahmed.publisher.erp.config.BridgeProperties;
import com.ahmed.publisher.erp.exceptions.integration.BridgeApiException;
import com.ahmed.publisher.erp.integration.bridge.dto.BridgePublicationRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Qualifier("bridgeRestTemplate")
public class BridgeCatalogClient {

    private static final Logger log = LoggerFactory.getLogger(BridgeCatalogClient.class);

    private final RestTemplate restTemplate;
    private final BridgeProperties bridgeProperties;

    public BridgeCatalogClient(
            @Qualifier("bridgeRestTemplate") RestTemplate restTemplate,
            BridgeProperties bridgeProperties
    ) {
        this.restTemplate = restTemplate;
        this.bridgeProperties = bridgeProperties;
    }

    public void pushPublications(BridgePublicationRequest publication) {
        String bridgeUrl = bridgeProperties.getUrl(); // get the URL here

        log.info("Sending publication to Bridge: {}", publication);

        var headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        var entity = new org.springframework.http.HttpEntity<>(publication, headers);

        try {
            var response = restTemplate.postForEntity(
                    bridgeUrl + "/catalog/publications",
                    entity,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BridgeApiException(
                        "Bridge push failed with status: " + response.getStatusCode()
                );
            }

            log.info("Bridge responded with status: {}, body: {}", response.getStatusCode(), response.getBody());

        } catch (Exception e) {
            log.error("Failed to push publication to bridge", e);
            throw new BridgeApiException("Failed to push publication to bridge: " + e.getMessage());
        }
    }
}
