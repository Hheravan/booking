package com.hsbc.demo.restursnt.reservation;

import com.hsbc.demo.restursnt.reservation.Resository.CustomerBookingRepository;
import com.hsbc.demo.restursnt.reservation.entities.CustomerBooking;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Stream;

@Service
class Initializer implements CommandLineRunner {

    private final CustomerBookingRepository repository;

    public Initializer(CustomerBookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Marlon Brando", "John Wayne", "Gregory Peck",
                "Paul Newman").forEach(name ->
                repository.save( CustomerBooking.builder()
                        .customerName(name)
                        .tableSize(4)
                        .bookDate(LocalDate.now())
                        .bookTime(10.00f).build())
        );
        repository.findAll().forEach(System.out::println);
    }
}