package com.cookie.yummy.repository;

import com.cookie.yummy.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//첫번째인자: 어떤 entity인자를 사용할것인가?
//두번째인자: @Id라는 어노테이션을 붙인 컬럼의 타입
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    //이메일로 회원정보 조회 메서드 정의
    //(select * from member_table where memberemail=?)라는 쿼리
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
