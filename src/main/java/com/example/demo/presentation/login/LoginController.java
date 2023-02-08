package com.example.demo.presentation.login;

import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * ログイン Controller
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {
	
	private final HttpServletRequest request;
	private final HttpSession session;

	private static final String GUEST_MEMBER_MAIL = "guest@example.com";
	private static final String GUEST_MEMBER_PASS = "guest";
	
	/**
	 * ログイン
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	/**
	 * ゲストログイン
	 * @return
	 * @throws ServletException
	 */
	@GetMapping("/login/guest")
	public String guestLogin() throws ServletException {
		
		// ゲストメンバーでログイン
		request.login(GUEST_MEMBER_MAIL, GUEST_MEMBER_PASS);
		log.info("User logged in. mail={}", GUEST_MEMBER_MAIL);
		
		// 予約内容確認(会員)へのリダイレクト対応
		SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		if (savedRequest != null) {
			// 認証成功後のリダイレクト先
			String redirectUrl = savedRequest.getRedirectUrl();
			
			if (redirectUrl.contains("/camping/member/reserve")) {
				return "redirect:/camping/member/reserve?confirm";
			}
		}
		
		return "redirect:/";
	}
}
