package org.gu.taskmanager.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

// @Component 表示这是一个组件，Spring 会自动管理它
@Component
public class JwtUtil {

    // 定义一个密钥，用于签名 JWT，请使用更安全的密钥
    private String SECRET_KEY = "your_secret_key";

    // 从 JWT 中提取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从 JWT 中提取过期时间
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 提取特定的声明（claim）
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 解析 JWT，提取所有的声明
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // 检查 JWT 是否过期
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 生成 JWT
    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    // 创建 JWT，设置用户名、签发时间、过期时间和签名算法
    private String createToken(String subject) {
        return Jwts.builder()
                .setSubject(subject) // 设置主题，这里是用户名
                .setIssuedAt(new Date(System.currentTimeMillis())) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 过期时间，这里是 10 小时
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 签名算法和密钥
                .compact();
    }

    // 验证 JWT 是否有效
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // 检查用户名是否匹配，且 JWT 是否未过期
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
