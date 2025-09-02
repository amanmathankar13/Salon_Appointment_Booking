package com.review.service;

import java.util.List;

import com.review.entity.Review;
import com.review.payload.dto.ReviewRequest;
import com.review.payload.dto.SalonDTO;
import com.review.payload.dto.UserDTO;

public interface ReviewService {
    // Define service methods here


    Review createReview(ReviewRequest reviewRequest, UserDTO userDTO, SalonDTO salonDTO);
    List<Review> getReviewsBySalonId(Long salonId);
    Review updateReview(Long reviewId, ReviewRequest reviewRequest, Long userId);
    void deleteReview(Long reviewId, Long userId);
    List<Review> getReviewsByUserId(Long userId);
}
