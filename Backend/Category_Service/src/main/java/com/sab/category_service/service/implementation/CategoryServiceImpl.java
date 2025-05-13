package com.sab.category_service.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sab.category_service.entity.Category;
import com.sab.category_service.payload.dto.SalonDTO;
import com.sab.category_service.repository.CategoryRepository;
import com.sab.category_service.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getCategoriesBySalonId(Long salonId) {
        return categoryRepository.findBySalonId(salonId);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(()-> new Exception("Category not found"));
    }

    @Override
    public String deleteCategory(Long id, Long salonId) throws Exception {
        Category category = getCategoryById(id);
        if(category.getSalonId()==salonId){
            categoryRepository.delete(category);
            return "Category deleted";
        }
        throw new Exception("You don't have permission to delete this category");
    }

    @Override
    public Category createCategory(Category category, SalonDTO salonDTO) {  // This method is used by salon owner only
        category.setSalonId(salonDTO.getId());
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category,SalonDTO salonDTO, Long id) throws Exception { // This method is used by salon owner only
        
        Category existingCategory = getCategoryById(id);
        if(salonDTO.getId()==existingCategory.getSalonId()){
            existingCategory.setName(category.getName());
            existingCategory.setImage(category.getImage());
            existingCategory.setSalonId(salonDTO.getId());
            existingCategory.setDescription(category.getDescription());
        }
        else{
            throw new Exception("You don't have permission to update this category");
        }
        return categoryRepository.save(existingCategory);

    }

}