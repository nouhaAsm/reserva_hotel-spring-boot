package com.applicacion.reserva.hotel.reserva.hotel.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.applicacion.reserva.hotel.reserva.hotel.dto.HotelDto;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.exception.ResourceNotFoundException;
import com.applicacion.reserva.hotel.reserva.hotel.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	private HotelService hotelService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public HotelController(HotelService hotelService) {
		this.hotelService = hotelService;
	}
	
	@GetMapping
	public ResponseEntity<List<HotelDto>> getAllHotels() {
		return ResponseEntity.ok().body(hotelService.getAllHotels().stream().map(hotel -> modelMapper.map(hotel, HotelDto.class))
				.collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<HotelDto> getHotelById(@PathVariable Integer id) throws ResourceNotFoundException {
		Hotel hotel = hotelService.getHotelById(id);
		HotelDto hotelResponse = modelMapper.map(hotel, HotelDto.class);
		return new ResponseEntity<HotelDto>(hotelResponse, HttpStatus.OK);
				
	}
	
	@PostMapping
	public ResponseEntity<HotelDto> addHotel(@RequestBody HotelDto hotelDto) {
		//Dto to entity
		Hotel hotelRequest = modelMapper.map(hotelDto, Hotel.class);
		Hotel hotel = hotelService.addHotel(hotelRequest);
		//Entity to Dto
		HotelDto hotelResponse = modelMapper.map(hotel, HotelDto.class);
		return new ResponseEntity<HotelDto>(hotelResponse, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<HotelDto> updateHotel(@PathVariable Integer id, @RequestBody HotelDto hotelDto) throws ResourceNotFoundException {
		Hotel hotelRequest = modelMapper.map(hotelDto, Hotel.class);
		Hotel hotel = hotelService.updateHotel(hotelRequest, id);
		HotelDto hotelResponse = modelMapper.map(hotel, HotelDto.class);
		return new ResponseEntity<HotelDto>(hotelResponse, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<HotelDto>> getAvailableHotels(@RequestParam(name = "name", required = false) String name, 
			@RequestParam(name = "category", required = false) Integer category,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date from, 
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date to) throws Exception {	
		
		return ResponseEntity.ok().body(hotelService.getAvailableHotels(from, to, name, category).stream().map(hotel -> modelMapper.map(hotel, HotelDto.class))
				.collect(Collectors.toList()));
		
	}

}
