package com.hsbc.demo.restursnt.reservation.service;

import com.hsbc.demo.restursnt.reservation.Resository.CustomerBookingRepository;
import com.hsbc.demo.restursnt.reservation.entities.CustomerBooking;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
@Service
public class BookingService {
    private final CustomerBookingRepository repository;

    public BookingService(CustomerBookingRepository repository) {
        this.repository = repository;
    }
    public List<CustomerBooking>  getBookings(){
        return  repository.findAll();
    }
    public List<CustomerBooking>  getBookingsByDate(String bookDate){
        if (bookDate==null||bookDate.isEmpty()){
            repository.findAll();
        }
        LocalDate date = null;
        try {
            date = LocalDate.parse(bookDate);
        } catch (DateTimeParseException e) {
            throw new RuntimeException(e);
        }
        return  repository.findByBookDate(date);
    }
    public CustomerBooking createBooking(CustomerBooking newBooking){
        CustomerBooking existingBooking = repository.findByCustomerNameAndBookDateAndBookTime(
                newBooking.getCustomerName()
                ,newBooking.getBookDate(),newBooking.getBookTime());
        if(existingBooking ==null){
            return repository.save(newBooking);

        }
        return existingBooking;
    }
    public CustomerBooking updateBooking(String id,CustomerBooking newBooking) {
        CustomerBooking existingBooking = repository.findByid(Long.parseLong(id));
        if (existingBooking == null) {
            return repository.save(newBooking);

        }
        return existingBooking;
    }
    public void deleteBooking(String id) {
        repository.deleteById(Long.parseLong(id));
    }
}
