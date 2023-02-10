package com.example.demo.application.service.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.Reservation;
import com.example.demo.domain.service.ReservationService;

import lombok.RequiredArgsConstructor;

/*
 * 予約内容の確認・キャンセル Application Service
 */
@RequiredArgsConstructor
@Service
public class ReservationsAppService {

	private final ReservationService reservationService;
	
	/**
	 * 予約検索(ページネーション)
	 * @param memberId 会員ID
	 * @param pageable ページ情報
	 * @return
	 */
	public Page<Reservation> searchReservations(int memberId, Pageable pageable) {
		return reservationService.searchReservations(memberId, pageable);
	}
	
	/**
	 * 予約詳細取得
	 * @param reservationId 予約ID
	 * @return
	 */
	public Reservation findReservationDetailsById(int reservationId) {
		
		return reservationService.findReservationDetailsById(reservationId)
				.orElseThrow(() -> new RuntimeException());
	}
	
	/**
	 * 予約キャンセル
	 * @param reservationId
	 * @return
	 */
	public int cancelReservation(int reservationId) {
		
		// 権限確認
		Reservation reservation = this.findReservationDetailsById(reservationId);
		
		// キャンセル済みまたはキャンセル期限を過ぎている場合はエラー
		if (reservation.isCanceled() || !reservation.canCancel()) {
			throw new RuntimeException();
		}
		
		// 予約キャンセル
		return reservationService.cancelReservation(reservationId);
	}
}
