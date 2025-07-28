package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.model.UserService;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService appUserService;

    @Value("${FRONTEND_URL:http://localhost:5173}")
    private String frontendUrl;

    // Explicit constructor for dependency injection
    @Autowired
    public SecurityConfig(UserService appUserService) {
        this.appUserService = appUserService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appUserService); // Ensure UserDetailsService is set
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // good in dev
                .formLogin(httpForm -> {
                    httpForm.loginPage("/req/login").permitAll();
                    httpForm.defaultSuccessUrl(frontendUrl, true);
                })
                .logout(logout -> {
                    logout.logoutUrl("/req/logout") // Define logout endpoint
                            .logoutSuccessUrl("/req/login") // Redirect to login page on logout
                            .invalidateHttpSession(true) // Invalidate session
                            .deleteCookies("JSESSIONID"); // Clear cookies
                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/admin/**").hasRole("ADMIN"); // 🛡️
                    registry.requestMatchers("/","/req/signup", "/index", "/css/**", "/images/**", "/js/**").permitAll();
                    // CHANGE API HERE: registry.requestMatchers("/api/readProgress/**", "/api/books/**").permitAll().authenticated;
                    /*also add other methods below*/
                    registry.requestMatchers(HttpMethod.POST, "/api/cards/save").authenticated();
                    registry.requestMatchers("/api/cards/**", "/api/books/**").authenticated();
                    registry.anyRequest().authenticated();
                })
                .cors(Customizer.withDefaults())
                .build();
    }

    // <<< ENTIRE BEAN FOR CORS CONFIGURATION
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // IMPORTANTE
        // PER PRODUZIONE
        // AUTORIZZA I DOMINI DA QUI VVV
        configuration.setAllowedOrigins(List.of(frontendUrl)); // <<< USE frontendUrl HERE
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID");
        serializer.setCookiePath("/");
        // IMPORTANT FOR LOCAL HTTP: Set secure to false.
        // For production with HTTPS, this MUST be true.
        serializer.setUseSecureCookie(false);
        serializer.setUseHttpOnlyCookie(true);
        // serializer.setDomainNamePattern("localhost");
        return serializer;
    }

    @Bean // ONLY FOR CHECK
    public CommandLineRunner urlCheckRunner() {
        return args -> {
            System.out.println("--- DIAGNOSTIC CHECK ---");
            System.out.println("Resolved frontendUrl from @Value: " + frontendUrl);
            System.out.println("--- END DIAGNOSTIC CHECK ---");
        };
    }
}
