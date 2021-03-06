package com.yoloo.server.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
public class TokenConfig {

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtTokenConverter());
  }

  @Bean
  protected JwtAccessTokenConverter jwtTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    Resource resource = new ClassPathResource("public.cert");
    String publicKey;
    try {
      publicKey =
          new String(
              FileCopyUtils.copyToByteArray(resource.getInputStream()), Charset.defaultCharset());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    converter.setVerifierKey(publicKey);
    return converter;
  }
}
