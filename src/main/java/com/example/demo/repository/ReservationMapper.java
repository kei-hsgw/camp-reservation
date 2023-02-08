package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Reservation;

@Mapper
public interface ReservationMapper {

	/**
	 * 予約登録
	 * @param reservation
	 * @return
	 */
	public int createReservation(Reservation reservation);
}
