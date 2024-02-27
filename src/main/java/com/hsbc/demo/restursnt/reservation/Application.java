package com.hsbc.demo.restursnt.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hsbc.demo.restursnt.reservation.Resository.CustomerBookingRepository;
import com.hsbc.demo.restursnt.reservation.entities.CustomerBooking;
import com.hsbc.demo.restursnt.reservation.service.BookingService;
import io.muserver.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import static io.muserver.MuServerBuilder.httpServer;

@SpringBootApplication
public class Application implements ApplicationContextAware {
	private  static CustomerBookingRepository repository;
	private static ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		BookingService bookingService = context.getBean(BookingService.class) ;
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);


		MuServer server = httpServer()
				.addHandler(Method.GET, "/api/bookings/{bookDate}", (req, resp, pathParams) -> {
					//get list of all reservations
					resp.write(objectMapper.writeValueAsString(bookingService.getBookingsByDate(pathParams.get("bookDate"))));
				})
				.addHandler(Method.POST, "/api/book", (req, resp, pathParams) -> {
					CustomerBooking newBooking = objectMapper.readValue(req.readBodyAsString(), CustomerBooking.class);

					resp.write(objectMapper.writeValueAsString(bookingService.createBooking(newBooking)));
				}).addHandler(Method.PUT, "/api/book/{id}", (req, resp, pathParams) -> {
					CustomerBooking newBooking = objectMapper.readValue(req.readBodyAsString(), CustomerBooking.class);

					resp.write(objectMapper.writeValueAsString(bookingService.updateBooking(pathParams.get("id"),newBooking)));
				}).addHandler(Method.DELETE, "/api/book/{id}", (req, resp, pathParams) -> {
					bookingService.deleteBooking(pathParams.get("id"));
					resp.write("Record Deleted Successfully! ");
				}).withHttpPort(8081)
				.start();
		System.out.println("Started server at " + server.uri());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
