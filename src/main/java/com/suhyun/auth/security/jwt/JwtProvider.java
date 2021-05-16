package com.suhyun.auth.security.jwt;

import com.suhyun.auth.security.services.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

  @Value("${suhyun.app.jwtSecret}")
  private String jwtSecret;

  @Value("${suhyun.app.jwtExpiration}")
  private int jwtExpiration;

  /**.
   * @param authentication JWT 토큰을 생성
   * @return Jwts.builder
   */
  public String generateJwtToken(Authentication authentication) {

    UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

    return Jwts.builder()
            .setSubject((userPrincipal.getUsername()))
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpiration*1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
  }

  /**.
   * @param authToken JWT 토큰을  검증하는곳
   * @return  조건에 맞을 경우는 true / error 발생시 false
   */
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature -> Message: {} ", e);
    }catch (ExpiredJwtException e) {
      logger.error("Expired JWT token -> Message: {}", e);
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token -> Message: {}", e);
    } catch (UnsupportedJwtException e) {
      logger.error("Unsupported JWT token -> Message: {}", e);
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty -> Message: {}", e);
    }

    return false;
  }

  /**.
   * @param token  JWT 토큰이 User에 정보를  가지고 있는곳
   * @return Jwt parser 호출
   */
  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody().getSubject();
  }
}