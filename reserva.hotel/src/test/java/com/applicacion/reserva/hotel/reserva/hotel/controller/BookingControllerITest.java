package com.applicacion.reserva.hotel.reserva.hotel.controller;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Booking;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.repository.BookingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerITest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
    private BookingRepository bookingRepository;

	@Autowired
	protected WebApplicationContext wac;

	@Test
    public void testGetBookingById() throws Exception {
		
		Booking booking = new Booking(1, new Date(), new Date(), 
				"test@gmail.com", new Hotel(1, "Hilton 1", 1));
		bookingRepository.save(booking);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/bookings/{id}", booking.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(booking.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(booking.getEmail()));
    }
	
	@Test
    public void testCreateBooking() throws JsonProcessingException, Exception {
		
		Booking booking = new Booking();
		booking.setId(3);
		booking.setEmail("test@gmail.com");
		booking.setFrom(new Date());
		booking.setTo(new Date());
		booking.setHotel(new Hotel(3, "SanminjaeOpens", 3));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)));

        response.andDo(MockMvcResultHandlers.print())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(booking.getId()))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(booking.getEmail()));
    }
	
	@Test
    public void testDeleteBooking() throws Exception {
		
		Booking booking = new Booking(1, new Date(), new Date(), 
				"test@gmail.com", new Hotel(1, "Hilton 1", 2));

		bookingRepository.save(booking);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/{id}", booking.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
