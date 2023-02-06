package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Member;

@Mapper
public interface MemberMapper {

	/**
	 * 会員情報取得
	 * @param mail メールアドレス
	 * @return
	 */
	public Member findByMail(String mail);
	
	/**
	 * 会員登録
	 * @param member 会員情報
	 */
	public void create(Member member);
}
