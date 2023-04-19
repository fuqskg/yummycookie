package com.cookie.yummy.entity;
//테이블 생성해줌

import com.cookie.yummy.dto.MemberDTO;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "member_table") // 클래스에 정의한대로 테이블이 생성되는데 테이블 이름을 정해준것
public class MemberEntity {

    @Id //pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "member_sn")
    private Long memberSn;

    @Column(nullable= false,unique = true)
    private String memberEmail; //가입id

    @Column
    private String memberName; //이름

    @Column(nullable = false, length = 100)
    private String memberPw; //비밀번호

    @Column(nullable = false, length = 100)
    private String memberPw1; //비밀번호 확인

    @Column
    private String memberPhone;//전화번호

    @Column
    private String memberPost; //우편번호

    @Column
    private String memberAddress1; //도로명주소

    @Column
    private String memberAddress2; //상세주소

    //db엔 roletype이라는 게 없음
    @Enumerated(EnumType.STRING)
    private RoleType role; //권한 (Enum을 쓰는 게 좋음)

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
