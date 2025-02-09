package com.example.exchange.security;

import com.example.exchange.entity.Authority;
import com.example.exchange.repository.CurrencyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurrencyUserService implements UserDetailsService {

  private final CurrencyUserRepository currencyUserRepository;

  @Autowired
  public CurrencyUserService(CurrencyUserRepository currencyUserRepository) {
    this.currencyUserRepository = currencyUserRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return currencyUserRepository
        .findByUsername(username)
        .map(
            user ->
                User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(
                        user.getAuthorities().stream()
                            .map(Authority::getAuthority)
                            .map(SimpleGrantedAuthority::new)
                            .toList())
                    .build())
        .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(username)));
  }


}
