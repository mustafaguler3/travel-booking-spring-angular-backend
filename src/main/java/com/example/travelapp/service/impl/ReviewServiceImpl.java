package com.example.travelapp.service.impl;

import com.example.travelapp.dto.HotelDto;
import com.example.travelapp.dto.ReviewDto;
import com.example.travelapp.dto.UserDto;
import com.example.travelapp.entity.Hotel;
import com.example.travelapp.entity.Review;
import com.example.travelapp.entity.User;
import com.example.travelapp.repository.HotelRepository;
import com.example.travelapp.repository.ReviewRepository;
import com.example.travelapp.repository.UserRepository;
import com.example.travelapp.service.ReviewService;
import com.example.travelapp.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<ReviewDto> getReviewsByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findHotelById(hotelId);

        if (hotel == null){
            //throw new RuntimeException("Hotel not found with id: " + hotel.getId());
            return null;
        }
        List<Review> reviews = reviewRepository.findReviewsByHotelId(hotelId);

        return reviews.stream().map(review -> dtoConverter.toReviewDto(review)).toList();
    }

    @Override
    public void addReview(ReviewDto reviewDto) {
        /*Review newReview = new Review();

        User user = userRepository.getById(reviewDto.getId());
        Hotel hotel = hotelRepository.findHotelById(reviewDto.getHotelId());

        newReview.setUser(user);
        newReview.setHotel(hotel);
        newReview.setRating(reviewDto.getRating());
        newReview.setComment(reviewDto.getComment());
        newReview.setPublishedDate(reviewDto.getPublishedDate());*/

        reviewRepository.save(dtoConverter.toReviewEntity(reviewDto));
    }

    @Override
    public Map<Integer, Long> getRatingCountsByHotelId(HotelDto hotelDto) {
        Hotel hotel = dtoConverter.toHotelEntity(hotelDto);

        var counting = reviewRepository.findByHotel(hotel)
                .stream()
                .collect(Collectors.groupingBy(Review::getRating,Collectors.counting()));

        return counting;
    }

    @Override
    public double getAverageRating(Long hotelId) {
        List<Review> reviews = reviewRepository.findReviewsByHotelId(hotelId);

        List<ReviewDto> reviewDtos = reviews.stream().map(review -> dtoConverter.toReviewDto(review)).toList();

        return reviewDtos.stream().mapToDouble(ReviewDto::getRating).average().orElse(0.0);
    }
}



