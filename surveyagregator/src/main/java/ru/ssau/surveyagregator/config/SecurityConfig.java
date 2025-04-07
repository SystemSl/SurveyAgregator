package ru.ssau.surveyagregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ssau.surveyagregator.handler.CustomAccessDeniedHandler;
import ru.ssau.surveyagregator.handler.CustomLogoutHandler;
import ru.ssau.surveyagregator.service.UserServiceImpl;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFIlter;

    private final UserServiceImpl userServiceImpl;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomLogoutHandler customLogoutHandler;

    public SecurityConfig(JwtFilter jwtFIlter,
                          UserServiceImpl userServiceImpl,
                          CustomAccessDeniedHandler accessDeniedHandler, CustomLogoutHandler customLogoutHandler) {
        this.jwtFIlter = jwtFIlter;
        this.userServiceImpl = userServiceImpl;
        this.accessDeniedHandler = accessDeniedHandler;
        this.customLogoutHandler = customLogoutHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login/**", "/registration/**", "/css/**", "/refresh_token/**", "/surveys/**", "/main/**")
                            .permitAll();
                    //auth.requestMatchers("/user/**").hasAuthority("USER");
                    auth.anyRequest().authenticated();
                }).userDetailsService(userServiceImpl)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(accessDeniedHandler);
                    e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtFIlter, UsernamePasswordAuthenticationFilter.class)
                .logout(log -> {
                    log.logoutUrl("/logout");
                    log.addLogoutHandler(customLogoutHandler);
                    log.logoutSuccessHandler((request, response, authentication) ->
                            SecurityContextHolder.clearContext());
                });

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}