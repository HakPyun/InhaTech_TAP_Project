package inhatc.project.tap.service;

import inhatc.project.tap.constant.Rank;
import inhatc.project.tap.entity.MemberEntity;
import inhatc.project.tap.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Service
public class MemberSecurityService  implements UserDetailsService{

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<MemberEntity>  member=memberRepository.findById(id);

        if(member.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");

        }
        MemberEntity memberEntity=member.get();
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(memberEntity.getRole().equals(Rank.ADMIN)){
         authorities.add(new SimpleGrantedAuthority(Rank.ADMIN.getValue()));
        }else if(memberEntity.getRole().equals(Rank.EDITOR)){
            authorities.add(new SimpleGrantedAuthority(Rank.EDITOR.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(Rank.USER.getValue()));

        }
        log.info("login success--------------"+authorities);
        return new User(memberEntity.getId(),memberEntity.getPw(),authorities);
    }


}
