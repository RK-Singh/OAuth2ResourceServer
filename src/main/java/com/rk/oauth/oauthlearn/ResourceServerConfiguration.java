package com.rk.oauth.oauthlearn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.util.Map;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // the resource id is the aud value of the JWT payload
        resources.resourceId("00000002-0000-0000-c000-000000000000");
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        String jwkSetUrl = "https://login.microsoftonline.com/common/discovery/keys";
        // implement the logic for custom claims validation
        // default is NoOps validator
        JwtClaimsSetVerifier jwtClaimsSetVerifier = new JwtClaimsSetVerifier() {
            @Override
            public void verify(Map<String, Object> claims) throws InvalidTokenException {
                String appId = "";
                if (claims.containsKey(appId) && claims.get(appId) instanceof String) {
                    String appIdClaim = (String) claims.get(appId);
                    //claims.put(, new Long(expiryInt));
                }
            }
        };
        //
        defaultTokenServices.setTokenStore(new JwkTokenStore(jwkSetUrl, jwtClaimsSetVerifier));
        return defaultTokenServices;
    }
}
