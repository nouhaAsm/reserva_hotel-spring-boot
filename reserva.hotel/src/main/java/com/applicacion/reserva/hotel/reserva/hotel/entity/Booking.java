package com.applicacion.reserva.hotel.reserva.hotel.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "Bookings")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "date_from")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date from;
	@Column(name = "date_to")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date to;
	@Column(name = "email")
	private String email;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hotel")
	private Hotel hotel;
	
	public Booking() {
		super();
	}

	public Booking(Integer id, Date from, Date to, String email, Hotel hotel) {
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
