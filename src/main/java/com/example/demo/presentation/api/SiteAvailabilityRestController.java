package com.example.demo.presentation.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.service.api.SiteAvailabilityRestAppService;

import lombok.RequiredArgsConstructor;

/*
 * サイト空き情報 Rest Controller
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule")
public class SiteAvailabilityRestController {

	private final SiteAvailabilityRestAppService siteAvailabilityRestAppService;
	
	/**
	 * サイト空き状況(スケジュール用)の検索
	 * @param siteTypeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GetMapping(value = "/siteTypes/{siteTypeId}", params = {"start", "end"})
	public List<ResultSiteAvailability> fetchSiteAvailabilityForSchedule(
			@PathVariable("siteTypeId") int siteTypeId,
			@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return siteAvailabilityRestAppService.fetchSiteAvailabilityForSchedule(siteTypeId, startDate, endDate);
	}
}
