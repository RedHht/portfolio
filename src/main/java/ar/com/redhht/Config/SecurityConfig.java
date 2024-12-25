package ar.com.redhht.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (SecurityFilterChain)httpSecurity
                .formLogin(Customizer.withDefaults())
                .rememberMe(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(c -> {
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)c.requestMatchers(new String[] { "/blog/new" })).authenticated();
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)c.requestMatchers(new String[] { "/blog/edit/*" })).authenticated();
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)c.requestMatchers(new String[] { "/**" })).permitAll();
                }).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return (PasswordEncoder)new BCryptPasswordEncoder();
    }
}