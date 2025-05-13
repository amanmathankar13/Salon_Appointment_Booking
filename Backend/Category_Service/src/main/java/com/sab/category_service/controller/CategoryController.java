package com.sab.category_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sab.category_service.entity.Category;
import com.sab.category_service.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/getBySalonId/{salonId}")
    public ResponseEntity<List<Category>> getCategoriesBySalonId(@PathVariable("salonId") Long salonId) {
        return ResponseEntity.ok(categoryService.getCategoriesBySalonId(salonId));
    }



}
