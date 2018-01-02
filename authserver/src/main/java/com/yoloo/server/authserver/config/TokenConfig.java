package com.yoloo.server.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class TokenConfig {

  private final Environment environment;

  @Autowired
  public TokenConfig(Environment environment) {
    this.environment = environment;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtTokenConverter());
  }

  @Bean
  protected JwtAccessTokenConverter jwtTokenConverter() {
    String pwd = environment.getProperty("encrypt.key-store.password");
    KeyStoreKeyFactory keyStoreKeyFactory =
        new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), pwd.toCharArray());
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
    return converter;
  }
}
