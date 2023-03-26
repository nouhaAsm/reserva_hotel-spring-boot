package com.applicacion.reserva.hotel.reserva.hotel.service;

import java.util.Date;
import java.util.List;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Booking;
import com.applicacion.reserva.hotel.reserva.hotel.exception.ResourceNotFoundException;

public interface BookingService {
	
	Booking createBooking(Booking booking);
	
	List<Booking> getBookingByHotel(Integer id_hotel, Date from, Date to); 
	
	Booking getBookingById(Integer id) throws ResourceNotFoundException;
	
	void cancelBooking(Integer id) throws ResourceNotFoundException;

}
