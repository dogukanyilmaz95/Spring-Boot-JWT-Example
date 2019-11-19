package com.dogukanyilmaz.authenticationauthorization.util;

/**
 * @author dogukanyilmaz
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class JwtUtil {
    private static String issuer = "dogukanyilmaz95@hotmail.com";
    private int expireTime = 3600000;
    private String environment = "prod";
    Date exp = null;


    public static String secret = "asdasxzcw423ho4VeKoBJZ6lJiF2kn3425432treJ3m4yXqX7i4kPdzGUDdbtoIBo-cV1PsneaqDlBBdsPhTfyCNan6qw2-82343dlmaşsdm23qZ1GJlHLcnQVyt-y0YcDoEExlGXww_pkeniUdrz9AAI6dD6fAI6LNCwefcaddl6qkn19-qpO6S-cy8XNlp2nfIu2iKyPwnTmffa9WcmBhwL28k8GQ6EyqMhPupOTx3R3PRtSpWVl-HKVJsaddsay05bP4TSMexkfP2LUwrnZ3d8-dUX_w";

    public JwtUtil(JwtUtil.Builder builder) {
        issuer = builder.issuer;
        this.environment = builder.environment;
        this.expireTime = builder.expireTime;
    }

    public static JwtUtil.Builder create() {
        return new JwtUtil.Builder();
    }

    private static Date dateAddMinute(Date date, int minute) {
        Date newdate = date;

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(12, minute);
            newdate = cal.getTime();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return newdate;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String generateTokenWithJWT(Map<String, String> fields) throws UnsupportedEncodingException {
        String jwt = "";
        this.exp = new Date(System.currentTimeMillis() + (long) this.expireTime);
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTCreator.Builder jwtBuilder = JWT.create().withIssuer(issuer).withExpiresAt(this.exp).withIssuedAt(new Date());
        Iterator var5 = fields.entrySet().iterator();

        while (var5.hasNext()) {
            Entry<String, String> entry = (Entry) var5.next();
            jwtBuilder.withClaim((String) entry.getKey(), (String) entry.getValue());
        }

        jwtBuilder.withClaim("env", this.environment);
        jwt = jwtBuilder.sign(algorithm);
        return jwt;
    }

    public static boolean verifyToken(String token) {
        boolean isValid = false;

        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT jwt = verifier.verify(token);
        isValid = jwt != null;

        return isValid;
    }

    public static String getDataFromKey(String token, String key) {
        return getDataFromKey(token, key, false);
    }

    public static String getDataFromKey(String token, String key, boolean checkJsonChar) {
        String data = "";

        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
        DecodedJWT jwt = verifier.verify(token);
        data = jwt.getClaim(key).asString();
        if (checkJsonChar) {
            data = data.replace("\"{", "{");
            data = data.replace("}\"", "}");
            data = data.replace("\"\"[", "[");
            data = data.replace("]\"\"", "]");
        }

        return data;
    }

    public static class Builder {
        private String issuer = "dogukanyilmaz95@hotmail.com";
        private int expireTime = 3600000;
        private String environment = "prod";

        public Builder() {
        }

        public JwtUtil build() {
            return new JwtUtil(this);
        }

        public Builder setIssuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public Builder setExpireTime(int expireTime) {
            this.expireTime = expireTime;
            return this;
        }

        public Builder setEnvironment(String env) {
            this.environment = env;
            return this;
        }
    }
}
