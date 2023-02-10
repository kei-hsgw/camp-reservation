package com.example.demo.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
	
	/**
	 * 予約検索(ページネーション)
	 * @param memberId 会員ID
	 * @param pageable ページ情報
	 * @return
	 */
	public Page<Reservation> searchReservations(int memberId, Pageable pageable) {
		
		// 指定した会員の予約情報取得
		List<Reservation> reservationList = reservationMapper.findPageByMemberId(memberId, pageable);
		// 指定した会員の予約数取得
		int count = reservationMapper.countByMemberId(memberId);
		
		return new PageImpl<>(reservationList, pageable, count);
	}
}
