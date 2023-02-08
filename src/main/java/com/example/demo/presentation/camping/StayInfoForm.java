package com.example.demo.presentation.camping;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*
 * 宿泊情報フォーム
 */
@Data
public class StayInfoForm {

	/** サイトタイプID */
	@NotNull
	private int siteTypeId;
	
	/** チェックイン日 */
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dateFrom;
	
	/** 滞在日数 */
	@NotNull
	@Min(1)
	@Max(10)
	private int stayDays;
	
	/** 人数 */
	@NotNull
	@Min(1)
	private int numberOfPeople;
	
	/** サイトタイプ名 */
	@NotBlank
	private String siteTypeName;
}
