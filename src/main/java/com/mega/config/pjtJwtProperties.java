// 파일: src/main/java/com/mega/config/JwtProperties.java
package com.mega.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix="jwt")
@Getter@Setter
public class pjtJwtProperties {
  private String secret;
  private long expiration;
}
