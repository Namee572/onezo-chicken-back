package com.green.onezo.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByUserIdAndPassword(String userId, String password);

    Optional<Member> findByUserId(String memberId);


    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByPhone(String phone);
}