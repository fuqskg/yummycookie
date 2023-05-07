//package com.cookie.yummy.config.auth;
//
//import com.cookie.yummy.entity.RoleType;
//import com.cookie.yummy.repository.MemberRepository;
//import com.cookie.yummy.entity.User;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.swing.text.html.Option;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PrincipalDetailService implements UserDetailsService {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    public PrincipalDetailService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    // 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
//    // password 부분 처리는 알아서 함.
//    // username이 DB에 있는지만 확인해주면 됨.
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> principal = this.memberRepository.findByUsername(username);
//        if (principal.isEmpty()){
//            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
//        }
//        User user = principal.get();
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if("admin".equals(username)){
//            authorities.add(new SimpleGrantedAuthority(RoleType.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(RoleType.USER.getValue()));
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
//
//
//
//    }
//}
