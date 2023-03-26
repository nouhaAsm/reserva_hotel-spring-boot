package com.applicacion.reserva.hotel.reserva.hotel.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class AvailabilityDto {

	private Integer id;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dateAv;
	private Integer id_hotel;
	private Integer rooms;
	
	public AvailabilityDto() {
		super();
	}
	
	public AvailabilityDto(Integer id, Date dateAv, Integer id_hotel, Integer rooms) {
		super();
		this.id = id;
		this.dateAv = dateAv;
		this.id_hotel = id_hotel;
		this.rooms = rooms;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_hotel() {
		return id_hotel;
	}
	public void setId_hotel(Integer id_hotel) {
		this.id_hotel = id_hotel;
	}
	
	public Date getDateAv() {
		return dateAv;
	}

	public void setDateAv(Date dateAv) {
		this.dateAv = dateAv;
	}

	public Integer getRooms() {
		return rooms;
	}
	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	
	
}
