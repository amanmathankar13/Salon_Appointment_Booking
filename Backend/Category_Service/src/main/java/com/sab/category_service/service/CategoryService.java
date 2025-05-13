package com.sab.category_service.service;

import java.util.List;

import com.sab.category_service.entity.Category;
import com.sab.category_service.payload.dto.SalonDTO;

public interface CategoryService {

    List<Category> getAllCategories();
    List<Category> getCategoriesBySalonId(Long salonId);
    Category getCategoryById(Long id) throws Exception;
    String deleteCategory(Long id, Long salonId) throws Exception;
    Category createCategory(Category category, SalonDTO salonDTO); // Salon Owner
    Category updateCategory(Category category, SalonDTO salonDTO, Long id) throws Exception;   // Salon Owner
}
