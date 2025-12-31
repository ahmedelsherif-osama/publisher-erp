package com.ahmed.publisher.erp.publication;

import com.ahmed.publisher.erp.publication.dto.PublicationRequest;
import com.ahmed.publisher.erp.publication.dto.PublicationResponse;

import java.util.List;
import java.util.UUID;

public interface PublicationService {

    PublicationResponse create(PublicationRequest request);

    PublicationResponse update(UUID id, PublicationRequest request);

    PublicationResponse getById(UUID id);

    List<PublicationResponse> getAll();

    void delete(UUID id);
}
