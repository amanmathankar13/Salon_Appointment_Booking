package com.sab.category_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sab.category_service.entity.Category;
import com.sab.category_service.payload.dto.SalonDTO;
import com.sab.category_service.service.CategoryService;
import com.sab.category_service.service.client.SalonFeignClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/category/salon-owner")
public class SalonCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SalonFeignClient salonFeignClient;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getByOwnerId(jwt).getBody();
        if (salonDTO == null) {
            throw new Exception("Salon not found for the user");
        }
        Category savedCategory = categoryService.createCategory(category, salonDTO);
        return ResponseEntity.ok(savedCategory);
    }

    @GetMapping("/salon/{salonId}/get/{id}")
    public ResponseEntity<Category> getCategoryByIdAndSalon(@PathVariable("salonId") Long salonId, @PathVariable("id") Long id) throws Exception {
        Category category = categoryService.getCategoryByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(category);
    }
    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) throws Exception{
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        categoryService.deleteCategory(id, salonDTO.getId());
        return ResponseEntity.ok("Category deleted");
    }
    
}
