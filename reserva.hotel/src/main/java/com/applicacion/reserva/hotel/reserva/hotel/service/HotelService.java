package com.applicacion.reserva.hotel.reserva.hotel.service;

import java.util.Date;
import java.util.List;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.exception.ResourceNotFoundException;

public interface HotelService {
	
	List<Hotel> getAllHotels();
	
	Hotel getHotelById(Integer id) throws ResourceNotFoundException;
	
	Hotel addHotel(Hotel hotel);
	
	Hotel updateHotel(Hotel hotel, Integer id) throws ResourceNotFoundException;
	
	List<Hotel> getAvailableHotels(Date from, Date to, String name, Integer category);
	

}
