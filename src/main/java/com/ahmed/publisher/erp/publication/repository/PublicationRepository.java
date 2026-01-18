package com.ahmed.publisher.erp.publication.repository;

import com.ahmed.publisher.erp.publication.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    boolean existsByIsbn(String isbn);
}
