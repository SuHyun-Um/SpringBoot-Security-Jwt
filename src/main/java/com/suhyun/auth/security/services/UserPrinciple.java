package com.suhyun.auth.security.services;

import com.suhyun.auth.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserPrinciple(Long id, String name,
                       String username, String email, String password,
                       Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  /**
   * .
   * @param user 에 대한 정보를 가지고 있는곳
   * @return UserPrinciple
   */
  public static UserPrinciple build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
            new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toList());

    return new UserPrinciple(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            authorities
    );
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  //계정의 이름을 리턴한다.
  @Override
  public String getUsername() {
    return username;
  }

  //계정의 비밀번호를 리턴한다.
  @Override
  public String getPassword() {
    return password;
  }

  //계정이 갖고있는 권한 목록을 리턴한다.
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  //계정이 만료되지 않았는 지 리턴한다. (true: 만료안됨)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //계정이 잠겨있지 않았는 지 리턴한다. (true: 잠기지 않음)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  // 비밀번호가 만료되지 않았는 지 리턴한다. (true: 만료안됨)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)
  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserPrinciple user = (UserPrinciple) o;
    return Objects.equals(id, user.id);
  }
}