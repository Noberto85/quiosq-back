package com.webone.quiosq.config;


import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private static final String SYSTEM_ADMIN = "SYSTEM_ADMIN";
    private static final String ADMIN = "ADMIN";
    private static final String GARCOM = "GARCOM";
    private static final String CLIENTE = "CLIENTE";
    private final UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
        "/success.html", "/api/webhook/**", "/api/v1/cliente/**",
        "/swagger-ui/**", "/swagger-ui/index.html", "/actuator/**",
        "/v3/api-docs/**", "/api/v1/qrcode/**", "/api/v1/auth/**",
        "/ws/**"
    };


    private static final String[] ENDPOINTS_WITH_ADMIN = {
        "/api/v1/admin/garcom/**", "/api/v1/admin/pedido/**", "/api/v1/admin/mesa/**"
        , "/api/v1/cardapio/**", "/api/v1/admin/item_cardapio/**", "/api/v1/admin/categoria/**",
        "/api/v1/admin/dashboard/**"
    };

    private static final String[] ENDPOINTS_CLIENTE_ADMIN = {
        "/api/v1/cardapio/**", "/api/v1/categoria", "/api/v1/pagamento/**", "/api/v1/pedido/**"
    };

    private static final String[] ENDPOINTS_SYSTEM_ADMIN = {
        "/api/v1/system/quiosque/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authoriza -> authoriza
                .requestMatchers(HttpMethod.GET, "/api/token").permitAll()
                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                .requestMatchers(ENDPOINTS_CLIENTE_ADMIN).hasAnyRole(ADMIN, CLIENTE)
                .requestMatchers(ENDPOINTS_WITH_ADMIN).hasAnyRole(ADMIN)
                .requestMatchers(ENDPOINTS_SYSTEM_ADMIN).hasAnyRole(SYSTEM_ADMIN)
                .anyRequest().authenticated()
            )
            .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .cors(Customizer.withDefaults())
            .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}