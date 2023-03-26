package com.applicacion.reserva.hotel.reserva.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel>{
	
	Hotel findByNameAndCategory(String name, Integer category);
	

}
