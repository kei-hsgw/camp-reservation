package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.domain.model.Member;
import com.example.demo.domain.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final MemberService memberService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			// 認証リクエストの設定
			.authorizeHttpRequests(auth -> auth
					// cssやjsなどの静的リソースをアクセス可能にする
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					// 認証の必要があるように設定
					.anyRequest().authenticated())
			.formLogin();
		return http.build();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		
		return mail -> {
			// ユーザ名を検索
			Member user = memberService.findByMail(mail);
			
			// ユーザ情報を返す
			return new User(user.getName(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		};
	}
}
