package com.example.demo.presentation.camping;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.application.service.camping.CampingAppService;
import com.example.demo.application.service.camping.ReserveAppService;
import com.example.demo.domain.model.Member;
import com.example.demo.domain.model.Reservation;
import com.example.demo.domain.model.StayInfo;
import com.example.demo.domain.model.UserInfo;
import com.example.demo.security.AuthenticatedMember;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/*
 * キャンプ情報表示・予約
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/camping")
public class CampingController {
	
	private final CampingAppService campingAppService;
	private final ReserveAppService reserveAppService;
	private final HttpSession session;
	private final ModelMapper modelMapper;
	
	@ModelAttribute
	public StayInfoForm setUpStayInfoForm() {
		return new StayInfoForm();
	}
	
	/**
	 * サイトタイプ一覧表示
	 * @param model
	 * @return
	 */
	@GetMapping("/siteTypes")
	public String siteTypeList(Model model) {
		
		model.addAttribute("siteTypeList", campingAppService.findAllSiteType());
		
		return "camping/siteTypes";
	}
	
	/**
	 * スケジュール表示
	 * @param siteTypeId
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/schedule", params = "siteTypeId")
	public String schedule(@RequestParam("siteTypeId") int siteTypeId, Model model) {
		
		model.addAttribute("siteTypeId", siteTypeId);
		model.addAttribute("siteTypeName", campingAppService.findSiteTypeName(siteTypeId));
		
		return "camping/schedule";
	}
	
	/**
	 * 宿泊情報フォーム表示
	 * @param stayInfoForm
	 * @return
	 */
	@GetMapping("/stayInfo")
	public String stayInfo(StayInfoForm stayInfoForm) {
		
		// サイトタイプ名取得
		String siteTypeName = campingAppService.findSiteTypeName(stayInfoForm.getSiteTypeId());
		stayInfoForm.setSiteTypeName(siteTypeName);
		
		return "camping/stayInfo";
	}
	
	/**
	 * 宿泊情報を予約内容確認（会員）に引き継ぎ
	 * @param stayInfoForm
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/stayInfo", params = "member")
	public String sendToReserve(@Validated StayInfoForm stayInfoForm, BindingResult result) {
		
		if (result.hasErrors()) {
			return "camping/stayInfo";
		}
		
		// セッション設定(リダイレクト後のリロード対策)
		session.setAttribute("stayInfoFormSession", stayInfoForm);
		// 予約内容確認(会員)は要認証
		return "redirect:/camping/member/reserve?confirm";
	}
	
	@GetMapping(value = "/member/reserve", params = "confirm")
	public String confirmByMember(@AuthenticationPrincipal AuthenticatedMember authenticatedMember, Model model) {
		
		// セッションから宿泊情報を取得
		StayInfoForm stayInfoForm = (StayInfoForm) session.getAttribute("stayInfoFormSession");
		StayInfo stayInfo = modelMapper.map(stayInfoForm, StayInfo.class);
		
		// 会員情報取得
		Member member = reserveAppService.findMemberById(authenticatedMember.getId());
		
		// 予約情報取得
		UserInfo userInfo = new UserInfo();
		userInfo.setId(member.getId());
		Reservation reservation = reserveAppService.buildReservation(stayInfo, userInfo);
		
		UserInfoForm userInfoForm = modelMapper.map(member, UserInfoForm.class);
		
		model.addAttribute("guestFlg", false);
		model.addAttribute("reservation", reservation);
		model.addAttribute("userInfoForm", userInfoForm);
		model.addAttribute("stayInfoForm", stayInfoForm);
		
		return "camping/confirm";
	}
	
	/**
	 * 宿泊情報を予約者情報フォーム表示（非会員）に引き継ぎ
	 * @param stayInfoForm
	 * @param result
	 * @return
	 */
	@PostMapping(value = "/stayInfo", params = "guest")
	public String sendToGuestReserve(@Validated StayInfoForm stayInfoForm, BindingResult result) {
		
		if (result.hasErrors()) {
			return "camping/stayInfo";
		}
		
		return "forword:/camping/guest/reserve?stayInfoForm";
	}
}
