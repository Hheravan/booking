package com.hsbc.demo.restursnt.reservation.Resository;

import com.hsbc.demo.restursnt.reservation.entities.CustomerBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerBookingRepository extends JpaRepository<CustomerBooking, Long> {
    CustomerBooking findByCustomerNameAndBookDateAndBookTime(String customerName, LocalDate bookDate, Float bookTime);
    List<CustomerBooking> findByBookDate(LocalDate bookDate);
    void deleteById(Long id);

    CustomerBooking findByid(long id);
}
