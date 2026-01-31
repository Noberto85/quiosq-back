package com.webone.quiosq.service.impl;

import com.webone.quiosq.entity.User;
import com.webone.quiosq.entity.enums.RoleName;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getNome().name()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (user.getRoles().stream().allMatch(
            rl -> rl.getNome().equals(RoleName.ROLE_ADMIN) || rl.getNome()
                .equals(RoleName.ROLE_SYSTEM_ADMIN))) {
            return user.getEmail();
        } else {
            return user.getCpf();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
