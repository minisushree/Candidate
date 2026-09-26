package com.example.inventorymngt.entity;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductEntitiy, Long> {
    List<ProductEntitiy> findByDeletedFalseOrderByIdAsc();
    Optional<ProductEntitiy> findByIdAndDeletedFalse(Long id);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProductEntitiy p where p.id = :id and p.deleted = false")
    Optional<ProductEntitiy> findByIdForUpdate(@Param("id") Long id);
}
