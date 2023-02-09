package com.example.demo.presentation.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

/*
 * ログイン Controller
 */
@RequiredArgsConstructor
@Controller
public class LoginController {
	
	/**
	 * ログイン
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
}
