package com.applicacion.reserva.hotel.reserva.hotel.controller;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.applicacion.reserva.hotel.reserva.hotel.service.AvailabilityService;

@RestController
@RequestMapping("/availabilities")
public class AvailabilityController {
	
	private AvailabilityService availabilityService;
	
	
	public AvailabilityController(AvailabilityService availabilityService) {
		super();
		this.availabilityService = availabilityService;
	}

	@PostMapping("/open")
	public void openAvailability(@RequestParam Integer id_hotel, 
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date from, 
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date to,
			@RequestParam Integer rooms) throws Exception {
		availabilityService.openAvailability(id_hotel, from, to, rooms);
	}
}
