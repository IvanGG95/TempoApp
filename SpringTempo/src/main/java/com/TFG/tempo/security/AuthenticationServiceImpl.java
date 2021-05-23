package com.TFG.tempo.security;

import com.TFG.tempo.data.entities.Role;
import com.TFG.tempo.data.entities.User;
import com.TFG.tempo.data.service.api.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationProvider {
  @Autowired
  UserService userService;

  @Override
  @Transactional
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String name = authentication.getName();
    String password = authentication.getCredentials().toString();

    User user = userService.findByUsername(name);

    if (user == null) {
      throw new AuthenticationServiceException("Invalid User");
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    }

    return new UsernamePasswordAuthenticationToken(name, password, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
