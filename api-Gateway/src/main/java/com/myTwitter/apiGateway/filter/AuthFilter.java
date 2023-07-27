package com.myTwitter.apiGateway.filter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import myTwitter.commons.dto.response.user.UserPrincipalResponse;
import myTwitter.commons.security.JwtAuthenticationException;
import myTwitter.commons.security.JwtProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static myTwitter.commons.constants.ErrorMessage.JWT_TOKEN_EXPIRED;
import static myTwitter.commons.constants.FeignConstants.USER_SERVICE;
import static myTwitter.commons.constants.PathConstants.*;

@Component

public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final JwtProvider jwtProvider;
    private final RestTemplate restTemplate;

    public AuthFilter(JwtProvider jwtProvider, RestTemplate restTemplate) {

        this.jwtProvider = jwtProvider;
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = jwtProvider.resolveToken(exchange.getRequest());
            boolean isTokenValid = jwtProvider.validateToken(token);

            if (token != null && isTokenValid) {
                String email = jwtProvider.parseToken(token);
                UserPrincipalResponse user = restTemplate.getForObject(
                        String.format("http://%s:8001%s", USER_SERVICE, API_V1_AUTH + USER_EMAIL),
                        UserPrincipalResponse.class,
                        email
                );

                if (user.getActivationCode() != null) {
                    throw new JwtAuthenticationException("Email not activated");
                }
                exchange.getRequest()
                        .mutate()
                        .header(AUTH_USER_ID_HEADER, String.valueOf(user.getId()))
                        .build();
                return chain.filter(exchange);
            } else {
                throw new JwtAuthenticationException(JWT_TOKEN_EXPIRED);
            }
        };
    }

    public static class Config {
    }
}