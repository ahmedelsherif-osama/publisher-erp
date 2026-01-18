package com.ahmed.publisher.erp.publication.repository;

import com.ahmed.publisher.erp.publication.entity.PublicationVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PublicationVariantRepository extends JpaRepository<PublicationVariant, UUID> {
    List<PublicationVariant> findByPublicationId(UUID publicationId);
}
