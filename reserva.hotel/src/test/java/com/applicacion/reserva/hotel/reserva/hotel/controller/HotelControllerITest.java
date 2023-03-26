package com.applicacion.reserva.hotel.reserva.hotel.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import com.applicacion.reserva.hotel.reserva.hotel.repository.HotelRepository;

import com.applicacion.reserva.hotel.reserva.hotel.Application;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HotelControllerITest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
    private HotelRepository hotelRepository;

	@Autowired
	protected WebApplicationContext wac;

	@Test
    public void testCreateHotel() throws Exception{

		Hotel hotel = new Hotel(1, "Cat1", 1);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hotel)));

        response.andDo(MockMvcResultHandlers.print())
        		.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(hotel.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(hotel.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(hotel.getCategory()));
    }
	
	@Test
	public void testGetHotelById() throws Exception {
		
		Hotel hotel = new Hotel(1, "Cat1", 1);
		hotelRepository.save(hotel);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/hotels/{id}", hotel.getId()));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Cat1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(1));

	}
	
	@Test
	public void testAllHotel() throws Exception {
		
        List<Hotel> listHotels = new ArrayList<>();
        listHotels.add(new Hotel(1, "Cat1", 1));
        listHotels.add(new Hotel(2, "Cat2", 2));
        listHotels.add(new Hotel(3, "Cat3", 3));
        listHotels.add(new Hotel(4, "Cat4", 4));
        listHotels.add(new Hotel(5, "Cat5", 5));
        listHotels.add(new Hotel(6, "Cat6", 6));
        
        hotelRepository.saveAll(listHotels);
        
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/hotels"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listHotels.size()));

	}
	
	@Test
    public void testUpdateHotel() throws Exception{
		
		Hotel savedHotel = new Hotel(1, "Cat1", 1);
		hotelRepository.save(savedHotel);
		
		Hotel updatedHotel = new Hotel(1, "Cat1_Max", 1);
		
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/hotels/{id}", savedHotel.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedHotel)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedHotel.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedHotel.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(updatedHotel.getCategory()));
    
    }

}
