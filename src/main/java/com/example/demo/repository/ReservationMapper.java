package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;

import com.example.demo.domain.model.Reservation;

@Mapper
public interface ReservationMapper {

	/**
	 * 予約登録
	 * @param reservation 予約
	 * @return
	 */
	public int createReservation(Reservation reservation);
	
	/**
	 * 予約情報取得
	 * @param memberId 会員ID
	 * @param pageable ページ情報
	 * @return
	 */
	public List<Reservation> findPageByMemberId(int memberId, Pageable pageable);
	
	/**
	 * 予約数取得
	 * @param memberId 会員ID
	 * @return
	 */
	public int countByMemberId(int memberId);
	
	/**
	 * 予約詳細取得
	 * @param reservationId 予約ID
	 * @return
	 */
	public Optional<Reservation> findReservationDetailsById(int reservationId);
	
	/**
	 * 予約キャンセル
	 * @param reservationId 予約ID
	 * @return
	 */
	public int cancelReservation(int reservationId);
}
