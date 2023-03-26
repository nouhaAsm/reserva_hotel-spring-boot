package com.applicacion.reserva.hotel.reserva.hotel.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Availability;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer>, JpaSpecificationExecutor<Availability> {

	List<Availability> findByDateAvBetween(Date from, Date to);
	
	List<Availability> findByHotel(Hotel hotel);

	List<Availability> findByDateAv(Date date);
}
