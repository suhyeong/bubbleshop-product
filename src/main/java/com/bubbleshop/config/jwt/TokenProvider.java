package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.StaticValues;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private SecretKey secretKey;

    @Value("${jwt.secret-key}")
    private String secretKeyValue;

    @PostConstruct
    protected void init() {
        String secret = Base64.getEncoder().encodeToString(secretKeyValue.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String resolveToken(HttpServletRequest request, String tokenName) {
        // Cookie 에서 Access Token 찾기
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            return !this.getClaims(token).getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException e) {
            e.printStackTrace();
            log.error("TokenProvider validateToken Error ! ", e);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims =  this.getClaims(token);

        Object authoritiesClaim = claims.get(StaticValues.Token.CLAIM_ROLE_KEY);
        String id = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities = ObjectUtils.isEmpty(authoritiesClaim) ?
                AuthorityUtils.NO_AUTHORITIES : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        return new UsernamePasswordAuthenticationToken(id, token, authorities);
    }

    public String getUserId(String token) {
        return this.getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
    }
}
