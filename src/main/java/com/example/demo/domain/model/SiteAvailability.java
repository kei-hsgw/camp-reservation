package com.example.demo.domain.model;

import java.time.LocalDate;

import lombok.Data;

/*
 * サイト空き状況
 */
@Data
public class SiteAvailability {

	/** 日にち */
	private LocalDate calendarDate;
	
	/** サイトタイプID */
	private int siteTypeId;
	
	/** 空き数 */
	private int availabilityCount;
	
	/** 最大数 */
	private int maxCount;
}
