package com.allancordeiro.creditanalysis.infrastructure.security.login.userdetails;

import com.allancordeiro.creditanalysis.usecase.customer.find.FindCustomerOutputDto;
import com.allancordeiro.creditanalysis.usecase.customer.login.LoginCustomerInputDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailData implements UserDetails {
    private final LoginCustomerInputDto loginCustomerInputDto;

    public UserDetailData(LoginCustomerInputDto input) {
        this.loginCustomerInputDto = input;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return this.loginCustomerInputDto.password();
    }

    @Override
    public String getUsername() {
        return this.loginCustomerInputDto.email();
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
