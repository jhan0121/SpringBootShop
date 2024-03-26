package com.flinter.shop.member;


import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> result = memberRepository.findByUsername(username);

        if (result.isEmpty()) {
            throw  new UsernameNotFoundException("존재하지 않는 아이디");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("normal"));

        Member user = result.get();
        CustomUser a = new CustomUser(user.getUsername(), user.getPassword(), authorities);
        a.setDisplayName(user.getDisplayName());

        return a;
    }

}


@Getter
@Setter
class CustomUser extends User {

    private String displayName;
    public CustomUser(String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}