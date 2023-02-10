package com.example.demo.presentation.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.application.service.member.ReservationsAppService;
import com.example.demo.domain.model.Reservation;
import com.example.demo.security.AuthenticatedMember;

import lombok.RequiredArgsConstructor;

/*
 * 予約内容確認・キャンセル Controller
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/member/reservations")
public class ReservationsController {

	private final ReservationsAppService reservationsAppService;
	
	/**
	 * 予約一覧
	 * @param authenticatedMember
	 * @param pageable
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(@AuthenticationPrincipal AuthenticatedMember authenticatedMember, @PageableDefault(size = 5) Pageable pageable, Model model) {
		
		// 予約検索(ページネーション)
		Page<Reservation> page = reservationsAppService.searchReservations(authenticatedMember.getId(), pageable);
		System.out.println(page.getContent());
		model.addAttribute("page", page);
		
		return "member/reservations/list";
	}
}
