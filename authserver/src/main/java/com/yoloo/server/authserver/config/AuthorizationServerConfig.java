package com.yoloo.server.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final TokenStore tokenStore;
  private final JwtAccessTokenConverter jwtAccessTokenConverter;

  @Autowired
  public AuthorizationServerConfig(
      AuthenticationManager authenticationManager,
      TokenStore tokenStore,
      JwtAccessTokenConverter jwtAccessTokenConverter) {
    this.authenticationManager = authenticationManager;
    this.tokenStore = tokenStore;
    this.jwtAccessTokenConverter = jwtAccessTokenConverter;
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient("web_app")
        .secret("secret")
        .scopes("read", "write")
        .autoApprove(true)
        .authorities("ROLE_ADMIN", "ROLE_USER")
        .authorizedGrantTypes("password", "refresh_token");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(tokenStore)
        .tokenEnhancer(jwtAccessTokenConverter)
        .authenticationManager(authenticationManager);
  }
}
