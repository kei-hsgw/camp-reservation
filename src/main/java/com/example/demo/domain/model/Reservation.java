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
	private BigDecimal totalAmountTaxIncl;
	
	/** 消費税 */
	private BigDecimal salesTax;
	
	/** 会員ID */
	private Integer memberId;
	
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
		setTotalAmountTaxIncl(totalAmountTaxInBeforeRounding.setScale(0, RoundingMode.FLOOR));
		setSalesTax(totalAmountTaxIncl.subtract(totalAmount));
	}
}
