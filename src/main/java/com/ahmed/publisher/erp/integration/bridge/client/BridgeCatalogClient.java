package com.ahmed.publisher.erp.integration.bridge.client;

import com.ahmed.publisher.erp.exceptions.BridgeApiException;
import com.ahmed.publisher.erp.integration.bridge.dto.BridgePublicationRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.ahmed.publisher.erp.exceptions.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@Qualifier("bridgeRestTemplate")
public class BridgeCatalogClient {

    private static final Logger log = LoggerFactory.getLogger(BridgeCatalogClient.class);

    private final RestTemplate restTemplate;
    private final String bridgeUrl;

    public BridgeCatalogClient(
            @Qualifier("bridgeRestTemplate") RestTemplate restTemplate,
            String bridgeUrl // inject via configuration
    ) {
        this.restTemplate = restTemplate;
        this.bridgeUrl = bridgeUrl;
    }

    public void pushPublications(BridgePublicationRequest publication) {
        log.info("Sending publication to Bridge: {}", publication);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BridgePublicationRequest> entity = new HttpEntity<>(publication, headers);

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
