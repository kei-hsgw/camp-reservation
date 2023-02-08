package com.example.demo.application.service.camping;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.Member;
import com.example.demo.domain.model.Reservation;
import com.example.demo.domain.model.ReservationDetail;
import com.example.demo.domain.model.SiteRate;
import com.example.demo.domain.model.StayInfo;
import com.example.demo.domain.model.UserInfo;
import com.example.demo.domain.service.MemberService;
import com.example.demo.domain.service.SiteRateService;

import lombok.RequiredArgsConstructor;

/*
 * キャンプ予約 Application Service
 */
@RequiredArgsConstructor
@Service
public class ReserveAppService {
	
	private final MemberService memberService;
	private final SiteRateService siteRateService;

	/**
	 * 会員情報取得（ID指定）
	 * @param memberId
	 * @return
	 */
	public Member findMemberById(int memberId) {
		
		return memberService.findById(memberId)
				.orElseThrow(() -> new RuntimeException());
	}
	
	/**
	 * 予約情報組み立て
	 * @param stayInfo
	 * @param userInfo
	 * @return
	 */
	public Reservation buildReservation(StayInfo stayInfo, UserInfo userInfo) {
		
		Reservation reservation = new Reservation(
				stayInfo.getSiteTypeId(),
				stayInfo.getDateFrom(),
				stayInfo.getStayDays(),
				stayInfo.getNumberOfPeople(),
				null,
				null,
				userInfo.getId(),
				userInfo.getName(),
				userInfo.getMail(),
				userInfo.getPhoneNumber());
		
		// 予約詳細リストを生成
		reservation.setReservationDetails(this.makeReservationDetail(stayInfo));
		reservation.calcTotalAmountTaxInAndSalesTax();
		
		return reservation;
	}
	
	/**
	 * 予約詳細リスト生成
	 * @param stayInfo
	 * @return
	 */
	private List<ReservationDetail> makeReservationDetail(StayInfo stayInfo) {
		
		List<LocalDate> dates = stayInfo.getDaysOfStay();
		
		return dates.stream().map(date -> {
			SiteRate siteRate = siteRateService.findSiteRate(stayInfo.getSiteTypeId(), date);
			
			return new ReservationDetail(date, siteRate);
		}).collect(Collectors.toList());
	}
}
