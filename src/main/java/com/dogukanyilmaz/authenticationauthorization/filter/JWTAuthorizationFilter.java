package com.dogukanyilmaz.authenticationauthorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dogukanyilmaz.authenticationauthorization.entity.Role;
import com.dogukanyilmaz.authenticationauthorization.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.dogukanyilmaz.authenticationauthorization.filter.JWTAuthenticationFilter.HEADER_STRING;
import static com.dogukanyilmaz.authenticationauthorization.filter.JWTAuthenticationFilter.TOKEN_PREFIX;

/**
 * @author dogukanyilmaz
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    String secret = "asdasxzcw423ho4VeKoBJZ6lJiF2kn3425432treJ3m4yXqX7i4kPdzGUDdbtoIBo-cV1PsneaqDlBBdsPhTfyCNan6qw2-82343dlmaşsdm23qZ1GJlHLcnQVyt-y0YcDoEExlGXww_pkeniUdrz9AAI6dD6fAI6LNCwefcaddl6qkn19-qpO6S-cy8XNlp2nfIu2iKyPwnTmffa9WcmBhwL28k8GQ6EyqMhPupOTx3R3PRtSpWVl-HKVJsaddsay05bP4TSMexkfP2LUwrnZ3d8-dUX_w";

    @Autowired
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user;
            try {
                user = JWT.require(Algorithm.HMAC512(secret))
                        .build()
                        .verify(token.replace(TOKEN_PREFIX, ""))
                        .getSubject();
            } catch (Exception e) {
                response.setStatus(401);
                return new UsernamePasswordAuthenticationToken(null, null, null);
            }

            token = token.replace(TOKEN_PREFIX, "").trim();
            String roles = JwtUtil.getDataFromKey(token, "roles");

            List<Role> roleList = new ArrayList<>();
            if (roles != null) {
                String[] arrRoles = roles.split(",");
                for (String stringRole : arrRoles) {
                    Role role = new Role();
                    role.setCode("ROLE_" + stringRole.trim().toUpperCase());
                    role.setName(stringRole.trim().toUpperCase());
                    roleList.add(role);
                }
            }
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, roleList);
            }
            return null;
        }
        return null;
    }


}
