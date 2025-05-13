package com.sab.category_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sab.category_service.entity.Category;
import com.sab.category_service.payload.dto.SalonDTO;
import com.sab.category_service.service.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/category/salon-owner")
public class SalonCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        Category savedCategory = categoryService.createCategory(category, salonDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) throws Exception{
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        categoryService.deleteCategory(id, salonDTO.getId());
        return ResponseEntity.ok("Category deleted");
    }
    
}
