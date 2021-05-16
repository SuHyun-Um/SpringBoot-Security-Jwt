package com.suhyun.auth.controller;

import com.suhyun.auth.model.Role;
import com.suhyun.auth.model.RoleName;
import com.suhyun.auth.model.User;
import com.suhyun.auth.repository.RoleRepository;
import com.suhyun.auth.repository.UserRepository;
import com.suhyun.auth.request.AuthForm;
import com.suhyun.auth.request.SignUpForm;
import com.suhyun.auth.response.JwtResponse;
import com.suhyun.auth.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtProvider jwtProvider;

  /**.
   * @param authRequest id/pass 인증
   * @return JwtResponse 호출
   */
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthForm authRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(),
            authRequest.getPassword()

        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtProvider.generateJwtToken(authentication);
    return ResponseEntity.ok(new JwtResponse(jwt));

  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
    if(userRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<String>("실패 -> Username 이 이미 존재 합니다.!",
              HttpStatus.BAD_REQUEST);
    }

    if(userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<String>("실패 -> Email 이 이미 존재 합니다.!",
              HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
            signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    strRoles.forEach(role -> {
      switch(role) {
        case "admin":
          Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                  .orElseThrow(() -> new RuntimeException(" 실패! -> 원인: 사용자의 권한이 일치하지 않습니다."));
          roles.add(adminRole);

          break;
        case "pm":
          Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                  .orElseThrow(() -> new RuntimeException(" 실패! -> 원인: 사용자의 권한이 일치하지 않습니다."));
          roles.add(pmRole);

          break;
        default:
          Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                  .orElseThrow(() -> new RuntimeException(" 실패! -> 원인: 사용자의 권한이 일치하지 않습니다."));
          roles.add(userRole);
      }
    });

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok().body(" 회원가입이 성공적으로 등록 되었습니다.!");
  }
}




