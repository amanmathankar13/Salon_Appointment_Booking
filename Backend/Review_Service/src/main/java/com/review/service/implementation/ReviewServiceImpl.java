package com.review.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.review.entity.Review;
import com.review.payload.dto.ReviewRequest;
import com.review.payload.dto.SalonDTO;
import com.review.payload.dto.UserDTO;
import com.review.repository.ReviewRepository;
import com.review.service.ReviewService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {


    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(ReviewRequest reviewRequest, UserDTO userDTO, SalonDTO salonDTO) {
        Review review = new Review();
        review.setRating(reviewRequest.getRating());
        review.setReviewText(reviewRequest.getReviewText());
        review.setUserId(userDTO.getId());
        review.setSalonId(salonDTO.getId());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsBySalonId(Long salonId) {
        return reviewRepository.findBySalonId(salonId);
    }


    private Review getReviewById(Long reviewId) throws Exception {
        return reviewRepository.findById(reviewId).orElseThrow(()-> new Exception("Review not found"));
    }

    @Override
    public Review updateReview(Long reviewId, ReviewRequest reviewRequest, Long userId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent() && review.get().getUserId().equals(userId)) {
            Review updatedReview = review.get();
            updatedReview.setRating(reviewRequest.getRating());
            updatedReview.setReviewText(reviewRequest.getReviewText());
            return reviewRepository.save(updatedReview);
        }
        return null;
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent() && review.get().getUserId().equals(userId)) {
            reviewRepository.delete(review.get());
        }
    }

    @Override
    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
}
