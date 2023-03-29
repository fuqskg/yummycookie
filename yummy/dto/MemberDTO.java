package com.cookie.yummy.dto;

import com.cookie.yummy.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long memberSn;
    private String memberName;
    private String memberEmail;
    private String memberPw;
    private String memberPw1;
    private String memberPhone;
    private String memberPost;
    private String memberAddress1;
    private String memberAddress2;


    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberSn(memberEntity.getMemberSn());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPw(memberEntity.getMemberPw());
        memberDTO.setMemberPw1(memberEntity.getMemberPw1());
        memberDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDTO.setMemberPost(memberEntity.getMemberPost());
        memberDTO.setMemberAddress1(memberEntity.getMemberAddress1());
        memberDTO.setMemberAddress2(memberEntity.getMemberAddress2());

        return memberDTO;


    }

}
