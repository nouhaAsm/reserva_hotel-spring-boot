package com.applicacion.reserva.hotel.reserva.hotel.controller;

import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.applicacion.reserva.hotel.reserva.hotel.dto.BookingDto;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Booking;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.exception.ResourceNotFoundException;
import com.applicacion.reserva.hotel.reserva.hotel.service.BookingService;
import com.applicacion.reserva.hotel.reserva.hotel.service.HotelService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	private BookingService bookingService;
	
	private HotelService hotelService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BookingController(BookingService bookingService, HotelService hotelService) {
		this.bookingService = bookingService;
		this.hotelService = hotelService;
	}

	@PostMapping
	public ResponseEntity<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
		List<Hotel> availableHotels = hotelService.getAvailableHotels(bookingDto.getFrom(), bookingDto.getTo(), null, null);
		if(!availableHotels.isEmpty()) {
			//Dto to entity
			Booking bookingRequest = modelMapper.map(bookingDto, Booking.class);
			Booking booking = bookingService.createBooking(bookingRequest);
			//Entity to Dto
			BookingDto bookingResponse = modelMapper.map(booking, BookingDto.class);
			return new ResponseEntity<BookingDto>(bookingResponse, HttpStatus.CREATED);
		}
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Booking>> checkReleasedBooking(
			@RequestParam(name = "id_hotel") Integer id_hotel,
			@RequestParam(name = "from") @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
			@RequestParam(name = "to") @DateTimeFormat(pattern="yyyy-MM-dd") Date to) throws ResourceNotFoundException {
		
		return ResponseEntity.ok().body(bookingService.getBookingByHotel(id_hotel, from, to));

				
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookingDto> getBookingById(@PathVariable Integer id) throws ResourceNotFoundException{
		Booking booking = bookingService.getBookingById(id);
		BookingDto bookingResponse = modelMapper.map(booking, BookingDto.class);
		return new ResponseEntity<BookingDto>(bookingResponse, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public void cancelBooking(@PathVariable Integer id) throws ResourceNotFoundException {
		bookingService.cancelBooking(id);
	}

}
