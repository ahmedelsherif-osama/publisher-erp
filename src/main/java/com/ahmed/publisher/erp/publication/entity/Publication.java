package com.ahmed.publisher.erp.publication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Publication {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String isbn;
    private String author;
    private String description;
    private boolean deleted = false;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PublicationVariant> variants;
}
