package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.ReservationDetail;

@Mapper
public interface ReservationDetailMapper {

	/**
	 * 予約詳細登録
	 * @param reservationDetails 予約詳細
	 * @param reservationId 予約ID
	 * @return
	 */
	public int createReservationDetails(List<ReservationDetail> reservationDetails, int reservationId);
}
