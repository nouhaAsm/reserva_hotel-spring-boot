package com.applicacion.reserva.hotel.reserva.hotel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Availability;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Booking;
import com.applicacion.reserva.hotel.reserva.hotel.exception.ResourceNotFoundException;
import com.applicacion.reserva.hotel.reserva.hotel.repository.AvailabilityRepository;
import com.applicacion.reserva.hotel.reserva.hotel.repository.BookingRepository;
import com.applicacion.reserva.hotel.reserva.hotel.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {
	
	private BookingRepository bookingRepo;
	
	private AvailabilityRepository availabilityRepo;

	public BookingServiceImpl(BookingRepository bookingRepo, AvailabilityRepository availabilityRepo) {
		this.bookingRepo = bookingRepo;
		this.availabilityRepo = availabilityRepo;
	}
	
	public List<Date> getListDates(Date fechaInicio, Date fechaFin) {
	    Calendar c1 = Calendar.getInstance();
	    c1.setTime(fechaInicio);
	    Calendar c2 = Calendar.getInstance();
	    c2.setTime(fechaFin);
	    List<Date> listaFechas = new ArrayList<Date>();
	    while (!c1.after(c2)) {
	        listaFechas.add(c1.getTime());
	        c1.add(Calendar.DAY_OF_MONTH, 1);
	    }
	    return listaFechas;
	}

	@Override
	public Booking createBooking(Booking booking) {
		Booking booki = bookingRepo.save(booking);
		for(Date date : getListDates(booking.getFrom(), booking.getTo())) {
			List<Availability> availabilities = availabilityRepo.findByDateAv(date);
			for(Availability ave : availabilities) {
				if(booking.getHotel().getId().equals(ave.getHotel().getId())) {
					Integer rooms = ave.getRooms()-1;
					ave.setRooms(rooms);
					availabilityRepo.save(ave);
				}
			}
		}
		return booki;
	}

	@Override
	public List<Booking> getBookingByHotel(Integer id_hotel, Date from, Date to) {
		Specification<Booking> spec = Specification.where(null);

		if (id_hotel != null) {
			Specification<Booking> hotelSpec = filtroHotel(id_hotel);
			spec = spec.and(hotelSpec);
		}

		if (from != null) {
			Specification<Booking> fechaFromSpec = filtroFechaFrom(from);
			spec = spec.and(fechaFromSpec);
		}
		
		if (from != null) {
			Specification<Booking> fechaToSpec = filtroFechaTo(to);
			spec = spec.and(fechaToSpec);
		}

		return bookingRepo.findAll(spec);
	}
	
	private Specification<Booking> filtroHotel(Integer id_hotel) {
		Specification<Booking> hotelSpec = (Root<Booking> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("hotel"), id_hotel);
		return hotelSpec;
	}

	private Specification<Booking> filtroFechaFrom(Date from) {
		Specification<Booking> fechaFromSpec = (Root<Booking> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("from"), from);
		return fechaFromSpec;
	}
	
	private Specification<Booking> filtroFechaTo(Date to) {
		Specification<Booking> fechaToSpec = (Root<Booking> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("to"), to);
		return fechaToSpec;
	}

	@Override
	public Booking getBookingById(Integer id) throws ResourceNotFoundException {
		Booking booking = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No booking found for this id"));
		return booking;
	}

	@Override
	public void cancelBooking(Integer id) throws ResourceNotFoundException {
		Booking booking = bookingRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No booking found for this id"));
		for(Date date : getListDates(booking.getFrom(), booking.getTo())) {
			List<Availability> availabilities = availabilityRepo.findByDateAv(date);
			for(Availability ave : availabilities) {
				if(booking.getHotel().getId().equals(ave.getHotel().getId())) {
					Integer rooms = ave.getRooms()+1;
					ave.setRooms(rooms);
					availabilityRepo.save(ave);
				}
			}
		}
		bookingRepo.deleteById(id);
	}

}
