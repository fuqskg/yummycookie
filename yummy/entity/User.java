package com.cookie.yummy.entity;
//테이블 생성해줌

//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "member_table") // 클래스에 정의한대로 테이블이 생성되는데 테이블 이름을 정해준것
public class User {

    @Id //pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "member_sn")
    private Long memberSn;

    @Column(nullable= false,unique = true)
    private String username; //가입id

    @Column(length = 500)
    private String password; //비밀번호

    @Column(nullable = false, length = 50)
    private String email; // myEmail, my_email

    //db엔 roletype이라는 게 없음
    @Enumerated(EnumType.STRING)
    private RoleType role; //권한 (Enum을 쓰는 게 좋음)

}
