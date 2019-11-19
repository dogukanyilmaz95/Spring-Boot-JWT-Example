package com.dogukanyilmaz.authenticationauthorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author dogukanyilmaz
 */
public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;


    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";

    public JWTAuthenticationFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));

        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JSONObject jsonObject;
        try {
            String marshalledXml = org.apache.commons.io.IOUtils.toString(request.getInputStream());

            jsonObject = new JSONObject(marshalledXml);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    jsonObject.get("username"),
                    jsonObject.get("password"),
                    new ArrayList<>()
            );


            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String secret = "asdasxzcw423ho4VeKoBJZ6lJiF2kn3425432treJ3m4yXqX7i4kPdzGUDdbtoIBo-cV1PsneaqDlBBdsPhTfyCNan6qw2-82343dlmaşsdm23qZ1GJlHLcnQVyt-y0YcDoEExlGXww_pkeniUdrz9AAI6dD6fAI6LNCwefcaddl6qkn19-qpO6S-cy8XNlp2nfIu2iKyPwnTmffa9WcmBhwL28k8GQ6EyqMhPupOTx3R3PRtSpWVl-HKVJsaddsay05bP4TSMexkfP2LUwrnZ3d8-dUX_w";
        final String authorities = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String token = JWT.create()
                .withSubject(authResult.getPrincipal().toString())
                .withClaim("roles", authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + 600000))
                .withIssuer("dogukanyilmaz95@hotmail.com")
                .sign(Algorithm.HMAC512(secret));
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(HEADER_STRING, TOKEN_PREFIX + token);
        out.print(jsonObject);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
    }
}
