package com.example.parkingSystem.services;


import com.example.parkingSystem.dao.BookingRepository;
import com.example.parkingSystem.dao.ParkingRepository;
import com.example.parkingSystem.dao.SubscriberRepository;
import com.example.parkingSystem.entity.Booking;
import com.example.parkingSystem.entity.Parking;
import com.example.parkingSystem.entity.Subscriber;
import com.example.parkingSystem.exceptions.ParkingNotFoundException;
import com.example.parkingSystem.exceptions.SubscriberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ParkingService parkingService;

    private final SubscriberService subscriberService;
    private final ParkingRepository parkingRepository;
    private final SubscriberRepository subscriberRepository;


    @Autowired
    public BookingService(BookingRepository bookingRepository, ParkingService parkingService, SubscriberService subscriberService, ParkingRepository parkingRepository, SubscriberRepository subscriberRepository) {
        this.bookingRepository = bookingRepository;
        this.parkingService = parkingService;
        this.subscriberService = subscriberService;
        this.parkingRepository = parkingRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public ResponseEntity<Booking> findBookingById(int bookingId){
        try {
           Booking foundBooking = bookingRepository.findBookingByBookingId(bookingId);
            if (foundBooking != null) {
                return ResponseEntity.ok(foundBooking);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public void saveBooking(Booking bookingToSave) throws ParkingNotFoundException, SubscriberNotFoundException{
        String carRegistration = bookingToSave.getSubscriberCarRegistration();
        int parkingId = bookingToSave.getParkingId();
        String bookingDate = bookingToSave.getBookingDate();
        if(!subscriberService.subscriberExist(carRegistration)) {
            throw new SubscriberNotFoundException();
        }

        if (!parkingService.parkingExist(parkingId)) {
            throw new ParkingNotFoundException();
        }


        Optional<Subscriber> optSubscriber = subscriberRepository.findById(carRegistration);
        Subscriber subscriber =  optSubscriber.get();
        Parking parking = parkingRepository.findByParkingId(parkingId);



        if (!subscriberService.hasLicense(carRegistration, parkingId)) {
            throw new IllegalStateException("Subskrybent o numerze rejestracyjnym " + carRegistration +
                    " nie posiada licencji na parking: " + parkingId);
        }
        if(!subscriberService.isSubscriptionActive(subscriber, bookingDate)){
            throw new IllegalStateException("Subskrybent nie posiada aktywnej subskrypcji na ten dzień");
        }


        if (!parkingService.listAvailableParkingsList(bookingDate).contains(parking)) {
            throw new IllegalStateException("W dniu " + bookingDate +
                    " nie mamy już miejsca na parking " + parkingId);
        }

        bookingRepository.save(bookingToSave);
    }



}

