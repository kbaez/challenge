package com.kbaez.challange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class AppConfig {

  @Configuration
  @PropertySource(value = "file:${data.properties.path}", encoding = "UTF-8")
  static class DataProperties
  {}
}
