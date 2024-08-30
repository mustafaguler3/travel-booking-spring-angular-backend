package com.example.travelapp.utils;

import com.example.travelapp.dto.*;
import com.example.travelapp.entity.*;
import com.example.travelapp.repository.PaymentRepository;
import com.example.travelapp.repository.RoomBookingRepository;
import com.example.travelapp.repository.RoomRepository;
import com.example.travelapp.repository.UserRepository;
import com.example.travelapp.request.PaymentRequest;
import com.example.travelapp.response.RoomBookingResponse;
import com.example.travelapp.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class DTOConverter {

    @Autowired
    private HotelService hotelService;
    //@Autowired
    //private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomBookingRepository roomBookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RoomRepository roomRepository;


    public User toEntity(UserDto userDto){
        User user = new User();

        List<Review> reviews = userDto
                .getReviewDtos().stream().map(this::toReviewEntity).toList();

        user.setReviews(reviews);
        user.setId(userDto.getId());
        user.setEnabled(userDto.isEnabled());
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());

        //user.setProfilePicture(userDto.getProfilePictureUrl());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto toDto(User user){
        if (user == null) {
            return null;  // Handle the case when user is null
        }
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());

        if (user.getReviews() != null) {
            List<ReviewDto> reviewDtos = user
                    .getReviews().stream().map(this::toReviewDto).toList();
            userDto.setReviewDtos(reviewDtos);
        }
        //userDto.setProfilePictureUrl(user.getProfilePicture());
        //userDto.setProfilePicture(user.getProfilePicture());

        userDto.setEnabled(user.isEnabled());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }

    public Review toReviewEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setId(reviewDto.getId());

        User user = userRepository.findUserById(reviewDto.getUserId());
        Hotel hotel = hotelService.findById(reviewDto.getHotelId());

        review.setUser(user);
        review.setHotel(hotel);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setPublishedDate(reviewDto.getPublishedDate());

        return review;
    }
    public ReviewDto toReviewDto(Review review){
        ReviewDto reviewDto = new ReviewDto();

        User user = userRepository.findUserById(reviewDto.getUserId());
        UserDto userDto = toDto(user);

        //reviewDto.setUsername(userDto.getUsername());
        //reviewDto.setProfilePicture(userDto.get);

        reviewDto.setProfilePicture(review.getUser().getProfilePicture());
        reviewDto.setUsername(review.getUser().getUsername());
        reviewDto.setUserId(review.getUser().getId()); // userD
        reviewDto.setHotelId(review.getHotel().getId()); // hotel

        reviewDto.setId(review.getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setPublishedDate(review.getPublishedDate());

        return reviewDto;
    }

    public Room toRoomEntity(RoomDto roomDto){
        Room room = new Room();
        Hotel hotel = hotelService.findById(roomDto.getHotelId());

        room.setId(roomDto.getId());
        room.setHotel(hotel);
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setRoomType(roomDto.getRoomType());
        room.setPricePerNight(roomDto.getPricePerNight());
        room.setCapacity(roomDto.getCapacity());
        room.setDescription(roomDto.getDescription());

        return room;
    }

    public RoomDto toRoomDto(Room room){
        RoomDto roomDto = new RoomDto();
        Hotel hotel = hotelService.findById(roomDto.getHotelId());

        roomDto.setId(room.getId());
        roomDto.setHotelId(hotel.getId());
        roomDto.setRoomNumber(room.getRoomNumber());
        roomDto.setRoomType(room.getRoomType());
        roomDto.setPricePerNight(room.getPricePerNight());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setDescription(room.getDescription());

        return roomDto;
    }

    public Payment toPaymentEntity(PaymentRequest paymentRequest){
        Payment payment = new Payment();
        payment.setCardNumber(paymentRequest.getCardNumber());
        payment.setPaymentType(paymentRequest.getPaymentType());
        payment.setPaymentStatus(paymentRequest.getPaymentStatus());
        payment.setPaymentAmount(paymentRequest.getPaymentAmount());
        payment.setPaymentDate(paymentRequest.getPaymentDate());
        payment.setCardExpiryDate(paymentRequest.getCardExpiryDate());
        payment.setCardHolderName(paymentRequest.getCardHolderName());
        payment.setCardSecurityCode(paymentRequest.getCardSecurityCode());
        payment.setCardNumber(paymentRequest.getCardNumber());
        payment.setPaypalEmail(payment.getPaypalEmail());

        return payment;
    }

    public PaymentRequest toPaymentDto(Payment payment){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setCardNumber(payment.getCardNumber());
        paymentRequest.setPaymentType(payment.getPaymentType());
        paymentRequest.setPaymentStatus(payment.getPaymentStatus());
        paymentRequest.setPaymentAmount(payment.getPaymentAmount());
        paymentRequest.setPaymentDate(payment.getPaymentDate());
        paymentRequest.setCardExpiryDate(payment.getCardExpiryDate());
        paymentRequest.setCardHolderName(payment.getCardHolderName());
        paymentRequest.setCardSecurityCode(payment.getCardSecurityCode());
        paymentRequest.setCardNumber(payment.getCardNumber());
        paymentRequest.setPaypalEmail(payment.getPaypalEmail());
        return paymentRequest;
    }

    public RoomBooking toRoomBooking(RoomBookingDto roomBookingDto){
        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setCountry(roomBookingDto.getCountry());
        roomBooking.setCity(roomBookingDto.getCity());
        roomBooking.setState(roomBookingDto.getState());
        roomBooking.setFirstName(roomBookingDto.getFirstName());
        roomBooking.setLastName(roomBookingDto.getLastName());
        roomBooking.setNote(roomBookingDto.getNote());
        roomBooking.setEmail(roomBookingDto.getEmail());
        roomBooking.setPhoneNumber(roomBookingDto.getPhoneNumber());
        roomBooking.setCheckInDate(roomBookingDto.getCheckInDate());
        roomBooking.setCheckOutDate(roomBookingDto.getCheckOutDate());



        roomBooking.setBookingReferenceNumber(roomBookingDto.getBookingReferenceNumber());


        return roomBooking;
    }
    public RoomBookingDto toRoombookingDto(RoomBooking roomBooking){
        RoomBookingDto roomBookingDto = new RoomBookingDto();
        roomBookingDto.setBookingId(roomBooking.getBookingId());
        roomBookingDto.setCountry(roomBooking.getCountry());
        roomBookingDto.setCity(roomBooking.getCity());
        roomBookingDto.setState(roomBooking.getState());
        roomBookingDto.setFirstName(roomBooking.getFirstName());
        roomBookingDto.setLastName(roomBooking.getLastName());
        roomBookingDto.setNote(roomBooking.getNote());
        roomBookingDto.setEmail(roomBooking.getEmail());
        roomBookingDto.setPhoneNumber(roomBooking.getPhoneNumber());
        roomBookingDto.setCheckInDate(roomBooking.getCheckInDate());
        roomBookingDto.setCheckOutDate(roomBooking.getCheckOutDate());
        roomBookingDto.setBookingReferenceNumber(roomBooking.getBookingReferenceNumber());

        roomBookingDto.setUserId(roomBooking.getUser().getId());
        roomBookingDto.setRoomId(roomBooking.getRoom().getId());
        roomBookingDto.setEmail(roomBooking.getEmail());
        roomBookingDto.setCity(roomBooking.getCity());
        roomBookingDto.setBookingStatus(roomBooking.getBookingStatus());
        roomBookingDto.setAdults(roomBooking.getAdults());
        roomBookingDto.setChildren(roomBooking.getChildren());
        roomBookingDto.setInfant(roomBooking.getInfant());
        roomBookingDto.setBookingType(roomBooking.getBookingType());
        roomBookingDto.setTotalPrice(roomBooking.getTotalPrice());

        return roomBookingDto;
    }

    public RoomBookingResponse roomBookingResponse(RoomBookingDto roomBookingDto){
        RoomBookingResponse roomBookingResponse = new RoomBookingResponse();



        roomBookingResponse.setFirstName(roomBookingDto.getFirstName());
        roomBookingResponse.setLastName(roomBookingDto.getLastName());
        roomBookingResponse.setEmail(roomBookingDto.getEmail());
        roomBookingResponse.setPhoneNumber(roomBookingDto.getPhoneNumber());
        roomBookingResponse.setCity(roomBookingDto.getCity());
        roomBookingResponse.setCountry(roomBookingDto.getCountry());
        roomBookingResponse.setState(roomBookingDto.getState());
        roomBookingResponse.setCreatedDate(LocalDate.now());
        roomBookingResponse.setPaymentType(roomBookingDto.getPaymentType());
        roomBookingResponse.setAddress(roomBookingDto.getAddress());
        roomBookingResponse.setLastUpdatedDate(LocalDate.now());

        roomBookingResponse.setBookingStatus(roomBookingDto.getBookingStatus());
        roomBookingResponse.setBookingReferenceNumber(roomBookingDto.getBookingReferenceNumber());
        roomBookingResponse.setTotalPrice(roomBookingDto.getTotalPrice());

        return roomBookingResponse;
    }

    public FlightDto toFlightDto(Flight flight){
        FlightDto flightDto = new FlightDto();
        flightDto.setFlightId(flight.getFlightId());
        flightDto.setAirlineName(flight.getAirlineName());
        flightDto.setFlightNumber(flight.getFlightNumber());
        flightDto.setFromAirport(flight.getFromAirport());
        flightDto.setToAirport(flight.getToAirport());
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setArrivalTime(flight.getArrivalTime());
        flightDto.setDepartureTime(flight.getDepartureTime());
        flightDto.setArrivalTime(flight.getArrivalTime());
        flightDto.setOriginalPrice(flight.getOriginalPrice());
        flightDto.setDiscountedPrice(flight.getDiscountedPrice());
        flightDto.setDiscountPercentage(flight.getDiscountPercentage());
        flightDto.setRefundPolicy(flight.getRefundPolicy());
        flightDto.setAirlineLogo(flight.getAirlineLogo());
        flightDto.setFromCity(flight.getFromCity());
        flightDto.setToCity(flight.getToCity());
        flightDto.setAirplaneType(flight.getAirplaneType());
        flightDto.setFlightDuration(flight.getFlightDuration());
        flightDto.setBaggageInfo(flight.getBaggageInfo());

        return flightDto;
    }
    public Flight toFlightEntity(FlightDto flightDto){
        Flight flight = new Flight();
        flight.setFlightId(flightDto.getFlightId());
        flight.setAirlineName(flightDto.getAirlineName());
        flight.setFlightNumber(flightDto.getFlightNumber());
        flight.setFromAirport(flightDto.getFromAirport());
        flight.setToAirport(flightDto.getToAirport());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setDepartureTime(flightDto.getDepartureTime());
        flight.setArrivalTime(flightDto.getArrivalTime());
        flight.setOriginalPrice(flightDto.getOriginalPrice());
        flight.setDiscountedPrice(flightDto.getDiscountedPrice());
        flight.setDiscountPercentage(flightDto.getDiscountPercentage());
        flight.setRefundPolicy(flightDto.getRefundPolicy());
        flight.setAirlineLogo(flightDto.getAirlineLogo());

        return flight;
    }

    public FlightBooking toFlightBooking(FlightBookingDto flightBookingDto){
        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setPassengerFirstName(flightBookingDto.getPassengerFirstName());
        flightBooking.setPassengerLastName(flightBookingDto.getPassengerLastName());
        flightBooking.setPassengerEmail(flightBookingDto.getPassengerEmail());
        flightBooking.setBookingId(flightBookingDto.getBookingId());
        flightBooking.setPassengerPassportNumber(flightBooking.getPassengerPhoneNumber());
        flightBooking.setPassengerPhoneNumber(flightBookingDto.getPassengerPhoneNumber());
        flightBooking.setArrivalDate(flightBookingDto.getArrivalDate());
        flightBooking.setDepartureDate(flightBookingDto.getDepartureDate());
        flightBooking.setArrivalAirport(flightBookingDto.getArrivalAirport());
        flightBooking.setDepartureAirport(flightBookingDto.getDepartureAirport());
        flightBooking.setFlightNumber(flightBookingDto.getFlightNumber());
        flightBooking.setAdult(flightBookingDto.getAdult());
        flightBooking.setChildren(flightBookingDto.getChildren());
        flightBooking.setInfant(flightBookingDto.getInfant());


        return flightBooking;
    }

    public FlightBookingDto toFlightBookingDto(FlightBooking flightBooking){
        FlightBookingDto flightBookingDto = new FlightBookingDto();
        flightBookingDto.setPassengerFirstName(flightBooking.getPassengerFirstName());
        flightBookingDto.setPassengerLastName(flightBooking.getPassengerLastName());
        flightBookingDto.setPassengerEmail(flightBooking.getPassengerEmail());
        flightBookingDto.setBookingId(flightBooking.getBookingId());
        flightBookingDto.setPassengerPassportNumber(flightBooking.getPassengerPassportNumber());
        flightBookingDto.setPassengerPhoneNumber(flightBooking.getPassengerPhoneNumber());
        flightBookingDto.setArrivalDate(flightBooking.getArrivalDate());
        flightBookingDto.setDepartureDate(flightBooking.getDepartureDate());
        flightBookingDto.setArrivalAirport(flightBooking.getArrivalAirport());
        flightBookingDto.setDepartureAirport(flightBooking.getFlight().getFromAirport());
        flightBookingDto.setFlightNumber(flightBooking.getFlightNumber());

        return flightBookingDto;
    }
}

















