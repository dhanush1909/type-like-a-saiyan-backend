package com.tla.saiyan.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            var jwt = parseJwt(httpServletRequest.getHeader("Authorization"));

            if (jwt != null) {

                var authentication = new BearerTokenAuthenticationToken(jwt);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage()); // TODO: replace with logs later.
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    private String parseJwt(String jwtWithBearer) {
        if (StringUtils.hasText(jwtWithBearer) && jwtWithBearer.startsWith("Bearer "))
            return jwtWithBearer.substring(7);
        return null;
    }
}
