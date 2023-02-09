package com.example.demo.repository;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Member;

@Mapper
public interface MemberMapper {

	/**
	 * 会員情報取得
	 * @param mail メールアドレス
	 * @return
	 */
	public Optional<Member> findByMail(String mail);
	
	/**
	 * 会員情報取得
	 * @param memberId 会員ID
	 * @return
	 */
	public Optional<Member> findById(int memberId);
	
	/**
	 * 会員登録
	 * @param member 会員情報
	 */
	public void createMember(Member member);
	
	/**
	 * 会員基本情報更新
	 * @param member 更新後会員情報
	 */
	public void updateProfile(Member member);
	
	/**
	 * メールアドレス変更
	 * @param member
	 */
	public void updateMail(Member member);
}
