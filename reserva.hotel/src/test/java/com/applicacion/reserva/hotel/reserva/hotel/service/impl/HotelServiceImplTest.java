package com.applicacion.reserva.hotel.reserva.hotel.service.impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.repository.AvailabilityRepository;
import com.applicacion.reserva.hotel.reserva.hotel.repository.HotelRepository;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

	@Mock
	private HotelRepository hotelRepoMock;
	
	@Mock
	private AvailabilityRepository availabilityRepoMock;
	
	private HotelServiceImpl hotelServiceImpl;
	
	@BeforeEach
	public void setup() {
		this.hotelServiceImpl = new HotelServiceImpl(hotelRepoMock, availabilityRepoMock);
	}
	
	@Test
	public void addHotel() {
		Hotel hotel = new Hotel(1, "Hilton", 2);
		Mockito.when(hotelRepoMock.save(hotel)).thenReturn(hotel);
		Hotel result = hotelServiceImpl.addHotel(hotel);
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateHotel() throws Exception {
		Hotel hotel = new Hotel(2, "Hilton Max", 2);
		Mockito.when(hotelRepoMock.save(Mockito.any(Hotel.class))).thenReturn(hotel);
		Mockito.when(hotelRepoMock.findById(1)).thenReturn(Optional.of(new Hotel(1, "Hilton 1", 2)));
		Hotel result = hotelServiceImpl.updateHotel(hotel, 1);
		Assertions.assertNotNull(result);
		Assertions.assertEquals("Hilton Max", hotel.getName());
	}
	
	@Test
	public void getHotelsList() {
		Mockito.when(hotelRepoMock.findAll()).thenReturn(List.of(new Hotel(1, "Hilton 1", 2), new Hotel(2, "Hilton 2", 3)));
		List<Hotel> hotelsList = hotelServiceImpl.getAllHotels();
		Assertions.assertNotNull(hotelsList);
		Assertions.assertTrue(hotelsList.size() > 0);
		Assertions.assertEquals(2, hotelsList.size());
		Mockito.verify(hotelRepoMock).findAll();
		Mockito.verifyNoMoreInteractions(hotelRepoMock);
	}
	
	@Test
	public void getHotelById() throws Exception {
		Mockito.when(hotelRepoMock.findById(1)).thenReturn(Optional.of(new Hotel(1, "Hilton 1", 2)));
		Hotel hotel = hotelServiceImpl.getHotelById(1);
		Assertions.assertNotNull(hotel);
		Assertions.assertEquals(1, hotel.getId());
		Mockito.verify(hotelRepoMock).findById(Mockito.any());
		Mockito.verifyNoMoreInteractions(hotelRepoMock);
	}
}
