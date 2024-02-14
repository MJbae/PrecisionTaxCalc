package com.o3.mj.controller;

import com.o3.mj.domain.customer.Encryptor;
import com.o3.mj.usecase.SearchCustomerService;
import com.o3.mj.usecase.dto.CustomerData;
import com.o3.mj.usecase.dto.CustomerQuery;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ROLE = "ROLE_USER";
    private final SearchCustomerService searchCustomerService;
    private final Encryptor encryptor = new Encryptor();

    public TokenAuthenticationFilter(SearchCustomerService searchCustomerService) {
        this.searchCustomerService = searchCustomerService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String token = trimBearerToken(request);
        return token == null || token.isEmpty();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = trimBearerToken(request);
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token is empty or invalid format");
        }

        try {
            String customerId = encryptor.decrypt(token);
            CustomerData customer = searchCustomerService.search(new CustomerQuery(customerId));
            List<GrantedAuthority> authority = AuthorityUtils.createAuthorityList(USER_ROLE);
            Authentication auth = new UsernamePasswordAuthenticationToken(customer, null, authority);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "authentication is failed");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String trimBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            return null;
        }

        return authorization.substring(7).trim();
    }
}
