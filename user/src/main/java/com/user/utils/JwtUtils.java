package com.user.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;


import java.util.Date;


@Component
public class JwtUtils {
    // 用于加密的私钥
    private final String secretKey = "zhule";
    public String getJWT(String userId) {
        long expirePeriod = 6 * 30 * 24 * 60 * 60 * 1000L;
        Date expireTime = new Date( new Date().getTime() + expirePeriod);
        return JWT
                .create()
                .withSubject(userId)
                .withExpiresAt( expireTime)
                .sign(Algorithm.HMAC256(secretKey));
    }
    public String parseJWT(String jwt) {
        try {
            DecodedJWT decodedjwt = JWT.require(Algorithm.HMAC256(secretKey))
                    .build().verify(jwt);
            return decodedjwt.getSubject();
        }
        catch (JWTVerificationException e) {
            throw new JWTVerificationException("用户未登录或登录过期");
        }
    }
}
