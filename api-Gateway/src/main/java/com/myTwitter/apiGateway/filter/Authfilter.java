package com.myTwitter.apiGateway.filter;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Authfilter extends AbstractGatewayFilterFactory<Authfilter.Config> {

    private final OAuth2ResourceServerProperties.Jwt jwt;
    private final RestTemplate restTemplate;

    public Authfilter(OAuth2ResourceServerProperties.Jwt jwt, RestTemplate rest){
        super(Config.class);
        this.jwt = jwt;
        this.restTemplate = rest;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String token = OAuth2ResourceServerProperties.Jwt.
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
