package com.cookie.yummy.entity;

import com.cookie.yummy.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "member_table") // 클래스에 정의한대로 테이블이 생성되는데 테이블 이름을 정해준것
public class MemberEntity {

    @Id //pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "member_sn")
    private Long memberSn;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberName;

    @Column
    private String memberPw;

    @Column
    private String memberPw1;

    @Column
    private String memberPhone;

    @Column
    private String memberPost;

    @Column
    private String memberAddress1;

    @Column
    private String memberAddress2;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        //dto에 담긴걸 entity객체로 넘기는 작업이기때문에
        //담기는 값은 DTO에 있는 값을 가져와야 함
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberPw(memberDTO.getMemberPw());
        memberEntity.setMemberPw1(memberDTO.getMemberPw1());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setMemberPost(memberDTO.getMemberPost());
        memberEntity.setMemberAddress1(memberDTO.getMemberAddress1());
        memberEntity.setMemberAddress2(memberDTO.getMemberAddress2());

        return memberEntity;
    }

}
