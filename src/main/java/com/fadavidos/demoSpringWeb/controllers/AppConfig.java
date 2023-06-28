package com.fadavidos.demoSpringWeb.controllers;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("app.config")
public record AppConfig(String header, String intro, List<UserAccount> users) {
}
