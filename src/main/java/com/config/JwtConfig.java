package com.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Jwt认证配置
 *
 * @author:HGF
 */
@ConfigurationProperties(prefix = "config.jwt")
@Component
@Data
@Slf4j
public class JwtConfig {
    /**
     * 密钥
     */
    private String secret;

    /**
     * token有效时间
     */
    private Long expire;

    /**
     * 字段名
     */
    private String header;

    /*
     * 根据身份ID标识，生成Token
     */
    public String getToken(String identityId) {
        Date nowDate = new Date();
        // token过期时间是一个小时
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(identityId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /*
     * 获取 Token 中注册信息
     */
    public Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("【token无效】" + e.getMessage());
            return null;
        }
    }

    /*
     * Token 是否过期验证
     */
    public boolean isTokenExpired(Date expirationTime) {
        return expirationTime.before(new Date());
    }

}
