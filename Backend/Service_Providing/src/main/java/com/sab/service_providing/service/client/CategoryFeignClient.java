package com.sab.service_providing.service.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sab.service_providing.payload.dto.CategoryDTO;

@FeignClient("CATEGORY_SERVICE")
public interface CategoryFeignClient {

    // @GetMapping("/category/get/{id}")
    // public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) throws Exception;

    @GetMapping("/category/salon-owner/salon/{salonId}/get/{id}")
    public ResponseEntity<CategoryDTO> getCategoryByIdAndSalon(@PathVariable("salonId") Long salonId, @PathVariable("id") Long id) throws Exception;
}
