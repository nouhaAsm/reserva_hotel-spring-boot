package com.applicacion.reserva.hotel.reserva.hotel.dto;

import java.util.Date;

import com.applicacion.reserva.hotel.reserva.hotel.entity.Hotel;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BookingDto {
	
	private Integer id;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date from;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date to;
	private String email;
	private Hotel hotel;
	
	public BookingDto() {
		super();
	}

	public BookingDto(Integer id, Date from, Date to, String email, Hotel hotel) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.email = email;
		this.hotel = hotel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	

}
