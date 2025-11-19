package com.ahmed.publisher.erp.repository;

import com.ahmed.publisher.erp.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
}
