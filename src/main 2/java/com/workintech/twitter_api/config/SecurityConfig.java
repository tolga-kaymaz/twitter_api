package com.workintech.twitter_api.config;

import com.workintech.twitter_api.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;

public class SecurityConfig {

    private UserDetailsServiceImpl userDetailsService;

    /**
     * BCrypt şifre encoder.
     * AuthService'de şifre hashleme, login'de şifre doğrulama için kullanılır.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * JDBC Authentication — kullanıcı bilgilerini veritabanından okur.
     * UserDetailsServiceImpl → UserRepository → user tablosu
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * AuthenticationManager — programatik login işlemleri için.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Security Filter Chain — tüm HTTP güvenlik kuralları burada.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // REST API olduğu için CSRF devre dışı
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth

                        // ── Herkese açık ────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/register", "/login")
                        .permitAll()

                        // ── Login olmadan okunabilir ─────────────────────────
                        .requestMatchers(HttpMethod.GET, "/tweet/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/comment/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/**")
                        .permitAll()

                        // ── Geri kalan tüm istekler login gerektirir ─────────
                        .anyRequest().authenticated()
                )

                // HTTP Basic Authentication
                // Header: Authorization: Basic <base64(username:password)>
                .httpBasic(Customizer.withDefaults())

                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
