package com.applicacion.reserva.hotel.reserva.hotel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Availability;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.repository.AvailabilityRepository;
import com.applicacion.reserva.hotel.reserva.hotel.service.AvailabilityService;


@Service
public class AvailabilityServiceImpl implements AvailabilityService {
	
	private AvailabilityRepository availabilityRepo;
	

	public AvailabilityServiceImpl(AvailabilityRepository availabilityRepo) {
		super();
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
	public void openAvailability(Integer id_hotel, Date from, Date to, Integer rooms) {
		
		List<Date> lisatadoFechas = getListDates(from, to);
		for (Date date : lisatadoFechas) {
			Optional<Availability> available = searchHotels(id_hotel, date);
			Availability availability = new Availability();
			Integer roomNumber = rooms;
			if(!available.isEmpty() && available.isPresent()) {
				availability.setId(available.get().getId());
				roomNumber = available.get().getRooms();	
				roomNumber+= rooms;	
			}
			availability.setHotel(new Hotel());
			availability.getHotel().setId(id_hotel);
			availability.setDateAv(date);
			availability.setRooms(roomNumber);
			availabilityRepo.save(availability);
		}
	}

	public Optional<Availability> searchHotels(Integer id_hotel, Date date) {
		Specification<Availability> spec = Specification.where(null);

		if (id_hotel != null) {
			Specification<Availability> hotelSpec = filtroHotel(id_hotel);
			spec = spec.and(hotelSpec);
		}

		if (date != null) {
			Specification<Availability> fechaFromSpec = filtroFecha(date);
			spec = spec.and(fechaFromSpec);
		}

		return availabilityRepo.findOne(spec);
	}
	
	public List<Availability> searchHotels(Date date) {
		Specification<Availability> spec = Specification.where(null);

		if (date != null) {
			Specification<Availability> fechaFromSpec = filtroFecha(date);
			spec = spec.and(fechaFromSpec);
		}

		return availabilityRepo.findAll(spec);
	}

	private Specification<Availability> filtroFecha(Date date_av) {
		Specification<Availability> date_avSpec = (Root<Availability> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("dateAv"), date_av);
		return date_avSpec;
	}
	
	private Specification<Availability> filtroHotel(Integer id_hotel) {
		Specification<Availability> hotelSpec = (Root<Availability> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("hotel"), id_hotel);
		return hotelSpec;
	}
	

}
