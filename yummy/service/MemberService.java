package com.cookie.yummy.service;

import com.cookie.yummy.dto.MemberDTO;
import com.cookie.yummy.entity.MemberEntity;
import com.cookie.yummy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;




    //회원가입처리
    public void join(MemberDTO memberDTO) {
        //1. dto -> entity 변환작업
        //2. repository의 join메소드 호출
        //MemberEntity.toMemberEntity(memberDTO);작성후 alt + enter
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity); //save메소드를 써야함
        //repository의 join메소드 호출(조건. entity객체를 넘기줘야 함)

    }


    public MemberDTO login(MemberDTO memberDTO) {
        /*
        1. 회원이 입력한 이메일로 db에서 조회를 함
        2. db에서 조회한 비밀번호와 사용자가 입력한 비밀번호 일치판단
         */
        //memberRepository.findByMemberEmail(memberDTO.getMemberEmail());를 작성하고
        //alt+enter누르면 optional객체로 리턴을 받는걸로 바뀌게 됨
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            //조회결과가 있음(해당이메일을 가진 회원정보가 있음)
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPw().equals(memberDTO.getMemberPw())){
                //비밀번호 일치
                //entity -> dto 변환 후 리턴 (dto클래스에 코드 작성)
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;

            } else {
                //비밀번호 불일치 (로그인 실패)
                return null;
            }
        } else {
            //조회결과가 없음(회원정보가 없음)
            return null;

        }
    }

}
