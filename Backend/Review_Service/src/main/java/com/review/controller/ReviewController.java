package com.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.review.entity.Review;
import com.review.payload.dto.ApiResponse;
import com.review.payload.dto.ReviewRequest;
import com.review.payload.dto.SalonDTO;
import com.review.payload.dto.UserDTO;
import com.review.service.ReviewService;
import com.review.service.client.SalonFeignClient;
import com.review.service.client.UserFeignClient;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final UserFeignClient userFeignClient;

    private final SalonFeignClient salonFeignClient;    

    @PostMapping("/{salonId}")
    public ResponseEntity<Review> createReview(@PathVariable("salonId") Long salonId, @RequestBody ReviewRequest reviewRequest,  @RequestHeader("Authorization") String token) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(token).getBody();
        SalonDTO salonDTO = salonFeignClient.getById(salonId).getBody();
        Review createdReview = reviewService.createReview(reviewRequest, userDTO, salonDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    @GetMapping("/get/salon/{salonId}")
    public ResponseEntity<List<Review>> getSalonById(@PathVariable("salonId") Long salonId) throws Exception {
        List<Review> reviews = reviewService.getReviewsBySalonId(salonId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<Review>> getUserById(@PathVariable("userId") Long userId) throws Exception {
        List<Review> reviews = reviewService.getReviewsByUserId(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable("id") Long id, @RequestBody ReviewRequest reviewRequest, @RequestHeader("Authorization") String token) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(token).getBody();
        Review updatedReview = reviewService.updateReview(id, reviewRequest, userDTO.getId());
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(token).getBody();
        reviewService.deleteReview(id, userDTO.getId());
        ApiResponse response = new ApiResponse();
        response.setMessage("Review deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
