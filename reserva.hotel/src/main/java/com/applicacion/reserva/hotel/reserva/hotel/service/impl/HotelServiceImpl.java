package com.applicacion.reserva.hotel.reserva.hotel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Availability;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.applicacion.reserva.hotel.reserva.hotel.exception.ResourceNotFoundException;
import com.applicacion.reserva.hotel.reserva.hotel.repository.AvailabilityRepository;
import com.applicacion.reserva.hotel.reserva.hotel.repository.HotelRepository;
import com.applicacion.reserva.hotel.reserva.hotel.service.HotelService;

@Service
public class HotelServiceImpl implements HotelService {

	private HotelRepository hotelRepo;
	
	private AvailabilityRepository availabilityRepo;
	
	public HotelServiceImpl(HotelRepository hotelRepo, AvailabilityRepository availabilityRepo) {
		this.hotelRepo = hotelRepo;
		this.availabilityRepo = availabilityRepo;
	}
	
	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepo.findAll();
	}

	@Override
	public Hotel getHotelById(Integer id) throws ResourceNotFoundException {
		Hotel hotel = hotelRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No hotel found for this id"));
		return hotel;
	}

	@Override
	@Transactional
	public Hotel addHotel(Hotel hotel) {
		return hotelRepo.save(hotel);
	}
	
	@Override
	@Transactional
	public Hotel updateHotel(Hotel hotel, Integer id) throws ResourceNotFoundException {
		Hotel h = hotelRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No hotel found for this id"));
		h.setId(id);
		h.setName(hotel.getName());
		h.setCategory(hotel.getCategory());
		return hotelRepo.save(h);
	}

	public List<Hotel> buscarNameAndCategory(String name, Integer category) {
		Specification<Hotel> spec = Specification.where(null);

		if (name != null) {
			Specification<Hotel> namelSpec = filtroHotelName(name);
			spec = spec.and(namelSpec);
		}

		if (category != null) {
			Specification<Hotel> categorySpec = filtroHotelCategory(category);
			spec = spec.and(categorySpec);
		}

		return hotelRepo.findAll(spec);
	}
	
	private Specification<Hotel> filtroHotelName(String name) {
		Specification<Hotel> nameSpec = (Root<Hotel> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name);
		return nameSpec;
	}

	private Specification<Hotel> filtroHotelCategory(Integer category) {
		Specification<Hotel> categorySpec = (Root<Hotel> root, CriteriaQuery<?> query,
				CriteriaBuilder criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
		return categorySpec;
	}
	
	public List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin) {
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
	public List<Hotel> getAvailableHotels(Date from, Date to, String name, Integer category) {
		List<Hotel> availableHotels = new ArrayList<>();
		Hotel hotel = hotelRepo.findByNameAndCategory(name, category);
		List<Date> lisatadoFechas = getListaEntreFechas(from, to);
		for (Date date : lisatadoFechas) {
			List<Availability> ave = availabilityRepo.findByDateAv(date);
			if (!ave.isEmpty()) {
				for (Availability av : ave) {
					if (!availableHotels.contains(av.getHotel())) {
						availableHotels.add(av.getHotel());
					}
				}
				break;
			}	
		}
		if(hotel != null) {
			availableHotels.removeIf(i -> !i.getName().equals(hotel.getName()) && 
					!i.getCategory().equals(hotel.getCategory()));
		}
		
		return availableHotels;
	}

}
