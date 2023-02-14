package com.example.demo.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.ReservationDetail;
import com.example.demo.repository.ReservationDetailMapper;

import lombok.RequiredArgsConstructor;

/*
 * 予約詳細 Domain Service
 */
@RequiredArgsConstructor
@Transactional
@Service
public class ReservationDetailService {

	private final ReservationDetailMapper reservationDetailMapper;
	
	/**
	 * 予約詳細登録
	 * @param reservationDetails 予約詳細
	 * @param reservationId 予約ID
	 * @return
	 */
	public int createReservationDetails(List<ReservationDetail> reservationDetails, int reservationId) {
		return reservationDetailMapper.createReservationDetails(reservationDetails, reservationId);
	}
}
