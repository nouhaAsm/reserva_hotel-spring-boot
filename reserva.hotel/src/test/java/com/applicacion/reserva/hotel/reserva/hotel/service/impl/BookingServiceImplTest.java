package com.applicacion.reserva.hotel.reserva.hotel.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Booking;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.repository.AvailabilityRepository;
import com.applicacion.reserva.hotel.reserva.hotel.repository.BookingRepository;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

	@Mock
	private BookingRepository bookingRepoMock;
	
	@Mock
	private AvailabilityRepository availabilityRepoMock;
	
	private BookingServiceImpl bookingServiceImpl;
	
	@BeforeEach
	public void setup() {
		this.bookingServiceImpl = new BookingServiceImpl(bookingRepoMock, availabilityRepoMock);
	}
	
	@Test
	public void addHotel() {
		Booking booking = new Booking(1, new Date(), new Date(), 
				"test@gmail.com", new Hotel(1, "Hilton 1", 2));
		
		Mockito.when(bookingRepoMock.save(booking)).thenReturn(booking);
		Booking result = bookingServiceImpl.createBooking(booking);
		Assertions.assertNotNull(result);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getBookingByHotel() {
		Mockito.when(bookingRepoMock.findAll(Mockito.any(Specification.class)))
				.thenReturn(List.of(new Booking(1, new Date(), new Date(), "test@gmail.com", new Hotel(1, "Hilton 1", 2)),
									new Booking(2, new Date(), new Date(), "test@gmail.com", new Hotel(1, "Hilton 2", 2))));
		List<Booking> bookingsList = bookingServiceImpl.getBookingByHotel(1, new Date(), new Date());
		Assertions.assertNotNull(bookingsList);
		Assertions.assertTrue(bookingsList.size() > 0);
		Mockito.verifyNoMoreInteractions(bookingRepoMock);
	}
	
	@Test
	public void getBookingById() throws Exception {
		Mockito.when(bookingRepoMock.findById(1))
			.thenReturn(Optional.of(new Booking(1, new Date(), new Date(), "test@gmail.com", new Hotel(1, "Hilton 1", 2))));
		Booking booking = bookingServiceImpl.getBookingById(1);
		Assertions.assertNotNull(booking);
		Assertions.assertEquals(1, booking.getId());
		Mockito.verify(bookingRepoMock).findById(Mockito.any());
		Mockito.verifyNoMoreInteractions(bookingRepoMock);
	}
	
	@Test
	public void cancelBooking() throws Exception {
		Mockito.when(bookingRepoMock.findById(1))
			.thenReturn(Optional.of(new Booking(1, new Date(), new Date(), "test@gmail.com", new Hotel(1, "Hilton 1", 2))));
		Mockito.doNothing().when(bookingRepoMock).deleteById(1);
		bookingServiceImpl.cancelBooking(1);
		Mockito.verify(bookingRepoMock).deleteById(1);
		Mockito.verifyNoMoreInteractions(bookingRepoMock);
	}
}
