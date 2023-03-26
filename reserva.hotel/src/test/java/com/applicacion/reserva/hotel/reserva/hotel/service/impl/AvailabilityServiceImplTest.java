package com.applicacion.reserva.hotel.reserva.hotel.service.impl;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Availability;
import com.applicacion.reserva.hotel.reserva.hotel.repository.AvailabilityRepository;
import com.applicacion.reserva.hotel.reserva.hotel.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
public class AvailabilityServiceImplTest {
	
	private AvailabilityServiceImpl availabilityServiceImpl;

	@Mock
	private AvailabilityRepository availabilityRepoMock;
	
	@Mock
	private HotelRepository hotelRepoMock;

	@BeforeEach
	public void setup() {
		this.availabilityServiceImpl = new AvailabilityServiceImpl(availabilityRepoMock);
	}

	@Test
	public void openAvailability() {
		Mockito.when(availabilityRepoMock.save(Mockito.any())).thenReturn(Mockito.any(Availability.class));
		availabilityServiceImpl.openAvailability(1, new Date(), new Date(), 2);
	}

}
