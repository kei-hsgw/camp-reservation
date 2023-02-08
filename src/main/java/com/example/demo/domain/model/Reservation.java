package com.example.demo.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 予約
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

	private static final int CANCEL_DEADLINE_NUMBER = 3;
	
	/** 予約ID */
	private Integer id;
	
	/** サイトタイプID */
	private Integer siteTypeId;
	
	/** チェックイン日 */
	private LocalDate dateFrom;
	
	/** 滞在日数 */
	private Integer stayDays;
	
	/** 人数 */
	private Integer numberOfPeople;
	
	/** 合計金額(税込) */
	private BigDecimal totalAmountTaxIn;
	
	/** 消費税 */
	private BigDecimal salesTax;
	
	/** 会員ID */
	private Integer memberId;
	
	/** 名前(非会員) */
	private String nonMemberName;
	
	/** メールアドレス(非会員) */
	private String nonMemberMail;
	
	/** 電話番号(非会員) */
	private String nonMemberPhoneNumber;
	
	/** キャンセル日 */
	private LocalDateTime canceledAt;
	
	/** 作成日 */
	private LocalDateTime createdAt;
	
	/** SiteTypeの情報 */
	private SiteType siteType;
	
	/** Memberの情報 */
	private Member member;
	
	/** ReservationDetailの情報 */
	private List<ReservationDetail> reservationDetails;
	
	public Reservation(
			Integer siteTypeId, LocalDate dateFrom, Integer stayDays,
			Integer numberOfPeople, BigDecimal totalAmountTaxIn, BigDecimal salesTax,
			Integer memberId, String nonMemberName, String nonMemberMail,
			String nonMemberPhoneNumber
	) {
		if (siteTypeId == null || dateFrom == null || stayDays == null ||
			numberOfPeople == null) {
			throw new IllegalArgumentException("必須項目が設定されていません。");
		}

		if (!isValidUserInfo(memberId, nonMemberName, nonMemberMail, nonMemberPhoneNumber)) {
			throw new IllegalArgumentException("引数が不正です。");
		}

		this.siteTypeId = siteTypeId;
		this.dateFrom = dateFrom;
		this.stayDays = stayDays;
		this.numberOfPeople = numberOfPeople;
		this.totalAmountTaxIn = totalAmountTaxIn;
		this.salesTax = salesTax;
		this.memberId = memberId;
		this.nonMemberName = nonMemberName;
		this.nonMemberMail = nonMemberMail;
		this.nonMemberPhoneNumber = nonMemberPhoneNumber;
	}
	
	/**
	 * ユーザー情報期限判定
	 * 会員IDが設定済みかつ非会員情報が設定済みの場合false
	 * 会員IDが未設定かつ非会員情報が設定済みの場合false
	 * それ以外はtrue
	 * @param memberId 会員ID
	 * @param nonMemberName 名前（非会員）
	 * @param nonMemberMail	メールアドレス（非会員）
	 * @param nonMemberPhoneNumber 電話番号（非会員）
	 * @return
	 */
	private boolean isValidUserInfo(Integer memberId, String nonMemberName, String nonMemberMail, String nonMemberPhoneNumber) {
		return (memberId != null && nonMemberName == null && nonMemberMail == null && nonMemberPhoneNumber == null)
				|| (memberId == null && nonMemberName != null && nonMemberMail != null && nonMemberPhoneNumber != null);
	}
	
	/**
	 * キャンセル期限判定
	 * キャンセル期限（チェックイン日の3日前まで）を過ぎている場合はfalse, それ以外はtrue
	 * @return
	 */
	public boolean canCancel() {
		return dateFrom.isAfter(LocalDate.now().plusDays(CANCEL_DEADLINE_NUMBER - 1));
	}
	
	/**
	 * キャンセル済み判定
	 * キャンセル済みの場合true, それ以外はfalse
	 * @return
	 */
	public boolean isCanceled() {
		return canceledAt != null;
	}
	
	/**
	 * 宿泊料金の合計額算出
	 * 予約詳細リストをもとに宿泊料金の合計額を算出し、totalAmountTaxIn、salesTaxフィールドを設定する
	 * 消費税率は宿泊日をもとに決定されるため、消費税率ごとに1度だけ積算したのち、合算する
	 */
	public void calcTotalAmountTaxInAndSalesTax() {

		// 消費税率毎に料金をグループ化して集計（消費税率改定またぎ対応）
		Map<BigDecimal, BigDecimal> map = getReservationDetails().stream().collect(
				Collectors.groupingBy(ReservationDetail::getTaxRate,
						Collectors.reducing(BigDecimal.ZERO, ReservationDetail::getSiteRate, BigDecimal::add)));

		// 合計金額（税抜）
		BigDecimal totalAmount = BigDecimal.ZERO;
		// 合計金額（税込）端数未処理
		BigDecimal totalAmountTaxInBeforeRounding = BigDecimal.ZERO;

		for (Map.Entry<BigDecimal, BigDecimal> entry : map.entrySet()) {
			// key:消費税率
			BigDecimal taxRate = entry.getKey();
			// value: 消費税率毎の集計金額
			BigDecimal totalByTaxRate = entry.getValue();

			// 合計金額（税抜）
			totalAmount = totalAmount.add(totalByTaxRate);

			// 合計金額（税抜）端数未処理
			totalAmountTaxInBeforeRounding = totalAmountTaxInBeforeRounding.add(
					BigDecimal.ONE.add(taxRate).multiply(totalByTaxRate));
		}

		// 合計金額（税込）端数処理完了
		setTotalAmountTaxIn(totalAmountTaxInBeforeRounding.setScale(0, RoundingMode.FLOOR));
		setSalesTax(totalAmountTaxIn.subtract(totalAmount));
	}
}