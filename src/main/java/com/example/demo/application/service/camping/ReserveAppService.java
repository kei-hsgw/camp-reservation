package com.example.demo.application.service.camping;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.model.Member;
import com.example.demo.domain.model.Reservation;
import com.example.demo.domain.model.ReservationDetail;
import com.example.demo.domain.model.SiteRate;
import com.example.demo.domain.model.StayInfo;
import com.example.demo.domain.model.UserInfo;
import com.example.demo.domain.service.MemberService;
import com.example.demo.domain.service.ReservationDetailService;
import com.example.demo.domain.service.ReservationService;
import com.example.demo.domain.service.SiteAvailabilityService;
import com.example.demo.domain.service.SiteRateService;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.SystemException;

import lombok.RequiredArgsConstructor;

/*
 * キャンプ予約 Application Service
 */
@RequiredArgsConstructor
@Transactional
@Service
public class ReserveAppService {
	
	private final MemberService memberService;
	private final SiteRateService siteRateService;
	private final ReservationService reservationService;
	private final ReservationDetailService reservationDetailService;
	private final SiteAvailabilityService siteAvailabilityService;
	private final MessageSource messageSource;

	/**
	 * 会員情報取得（ID指定）
	 * @param memberId 会員ID
	 * @return
	 */
	public Member findMemberById(int memberId) {
		
		return memberService.findById(memberId)
				.orElseThrow(() -> new SystemException(messageSource.getMessage("exception.dataNotFound", new String[] {String.valueOf(memberId)}, Locale.JAPAN)));
	}
	
	/**
	 * 予約情報組み立て
	 * @param stayInfo 宿泊情報
	 * @param userInfo 会員情報
	 * @return
	 */
	public Reservation buildReservation(StayInfo stayInfo, UserInfo userInfo) {
		
		Reservation reservation = new Reservation();
		reservation.setSiteTypeId(stayInfo.getSiteTypeId());
		reservation.setDateFrom(stayInfo.getDateFrom());
		reservation.setStayDays(stayInfo.getStayDays());
		reservation.setNumberOfPeople(stayInfo.getNumberOfPeople());
		reservation.setMemberId(userInfo.getId());
		reservation.setReservationDetails(this.makeReservationDetail(stayInfo));
		reservation.calcTotalAmountTaxInAndSalesTax();
		
		return reservation;
	}
	
	/**
	 * 予約詳細リスト生成
	 * @param stayInfo 宿泊情報
	 * @return
	 */
	private List<ReservationDetail> makeReservationDetail(StayInfo stayInfo) {
		
		List<LocalDate> dates = stayInfo.getDaysOfStay();
		
		return dates.stream().map(date -> {
			SiteRate siteRate = siteRateService.findSiteRate(stayInfo.getSiteTypeId(), date);
			
			return new ReservationDetail(date, siteRate);
		}).collect(Collectors.toList());
	}
	
	/**
	 * キャンプ予約 
	 * サイト空き状況の在庫を減らし、予約調整を行う
	 * @param reservation 予約
	 */
	public void saveReservation(Reservation reservation) {
		
		StayInfo stayInfo = new StayInfo();
		stayInfo.setSiteTypeId(reservation.getSiteTypeId());
		stayInfo.setDateFrom(reservation.getDateFrom());
		stayInfo.setStayDays(reservation.getStayDays());
		stayInfo.setNumberOfPeople(reservation.getNumberOfPeople());
		
		// サイト空き状況の在庫を減らし、サイトを確保
		this.reduceAvailabilityCount(stayInfo);
		// 予約登録
		reservationService.createReservation(reservation);
		// 予約詳細登録
		reservationDetailService.createReservationDetails(reservation.getReservationDetails(), reservation.getId());
	}
	
	/**
	 * サイト空き状況減算処理
	 * @param stayInfo 宿泊情報
	 */
	private void reduceAvailabilityCount(StayInfo stayInfo) {
		
		int updateCount = siteAvailabilityService.reduceAvailabilityCount(stayInfo.getSiteTypeId(), stayInfo.getDateFrom(), stayInfo.getDateTo());
		
		if (updateCount != stayInfo.getStayDays()) {
			throw new BusinessException(messageSource.getMessage("exception.siteIsNotAvailable", null, Locale.JAPAN));
		}
	}
}
