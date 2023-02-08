package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.ReservationDetail;
import com.example.demo.repository.ReservationDetailMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationDetailService {

	private final ReservationDetailMapper reservationDetailMapper;
	
	/**
	 * 予約詳細登録
	 * @param reservationDetails
	 * @param reservationId
	 * @return
	 */
	public int createReservationDetails(List<ReservationDetail> reservationDetails, int reservationId) {
		return reservationDetailMapper.createReservationDetails(reservationDetails, reservationId);
	}
}
