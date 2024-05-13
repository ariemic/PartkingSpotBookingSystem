package com.example.parkingSystem.config;

import com.example.parkingSystem.dao.BookingRepository;
import com.example.parkingSystem.entity.Booking;
import com.example.parkingSystem.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

//@Validated
@Controller
public class DataBookingController {
    private final BookingRepository bookingRepository;
    private final ParkingService parkingService;

    @Autowired
    public DataBookingController(BookingRepository bookingRepository, ParkingService parkingService) {
        this.bookingRepository = bookingRepository;
        this.parkingService = parkingService;
    }
//
//
//    @PostMapping("/bookings")
//    public ResponseEntity<String> saveBooking(@Validated @RequestBody List<Booking> bookingsToSave){
//        try {
//            for(Booking booking: bookingsToSave){
//                if(!parkingService.listAvailableParkings(booking.getBookingDate()).contains(booking.getParking())){
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Na parkingu " + booking.getParking().getAddress() + "nie ma już miejsca w ten dzień");
//                }
//            }
//            bookingRepository.saveAll(bookingsToSave);
//            return ResponseEntity.ok("Pomyślnie zapisano ");
//        } catch (Exception ex){
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wystąpił błąd walidacji danych" + ex);
//        }
//
//
//    }
//
//    @GetMapping("/bookings")
//    public ResponseEntity<List<Booking>> bookingList(){
//        List<Booking> bookingList = bookingRepository.findAll();
//        return ResponseEntity.ok(bookingList);
//    }




}
