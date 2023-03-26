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


@Entity
@Table(name = "Availabilities")
public class Availability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "date_av")
	private Date dateAv;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_hotel")
	private Hotel hotel;
	@Column(name = "rooms")
	private Integer rooms;
	
	public Availability() {
		super();
	}

	public Availability(Integer id, Date dateAv, Hotel hotel, Integer rooms) {
		super();
		this.id = id;
		this.dateAv = dateAv;
		this.hotel = hotel;
		this.rooms = rooms;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateAv() {
		return dateAv;
	}

	public void setDateAv(Date dateAv) {
		this.dateAv = dateAv;
	}

	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Integer getRooms() {
		return rooms;
	}

	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}
	
	

}
