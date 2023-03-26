package com.applicacion.reserva.hotel.reserva.hotel.service;

import java.util.Date;

public interface AvailabilityService {
	
	void openAvailability(Integer id_hotel, Date from, Date to, Integer rooms);

}
