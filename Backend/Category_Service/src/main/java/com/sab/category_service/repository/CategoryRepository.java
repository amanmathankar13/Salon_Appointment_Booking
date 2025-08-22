package com.sab.category_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sab.category_service.entity.Category;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findBySalonId(Long salonId);
    Optional<Category> findByIdAndSalonId(Long id, Long salonId);
}
