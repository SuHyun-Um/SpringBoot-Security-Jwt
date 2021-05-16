package com.suhyun.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

  @GetMapping("/api/test/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public String userAccess() {
    return ">>> Hello User Page 에 온걸 환영 합니다!";
  }

  @GetMapping("/api/test/pm")
  @PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Helllo PM Page에 온걸 환영 합니다!";
  }

  @GetMapping("/api/test/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return ">>> Hello ADMIN page 에 온걸 환영 합니다.!";
  }
}


// 반환되는 사용자의 이름과 현재 사용자의 이름이 일치하지 않을 경우  또는 현재 사용자가 관리자 권한을 들고있습니까?
//이 조건을 만족하는 사용자의 경우에만 응답할 수 있습니다. 아니라면 403 에러로 응답해드립니다.