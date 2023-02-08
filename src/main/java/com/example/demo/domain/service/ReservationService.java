package com.example.demo.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Reservation;
import com.example.demo.repository.ReservationMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService {
	
	private final ReservationMapper reservationMapper;

	/**
	 * 予約登録
	 * @param reservation
	 * @return
	 */
	public int createReservation(Reservation reservation) {
		return reservationMapper.createReservation(reservation);
	}
}
