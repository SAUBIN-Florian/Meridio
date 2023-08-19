package dev.florian.meridio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SpringSecurity(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> {
                csrf.disable();
                // csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"));
                // csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            })
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
                auth.requestMatchers("/css/**", "/js/**").permitAll();
                auth.requestMatchers("/auth/register").permitAll();
                auth.requestMatchers("/**").authenticated();
                auth.anyRequest().denyAll();
            })
            .userDetailsService(this.userDetailsService)
            .formLogin(form -> form.loginPage("/auth/login").permitAll())
            .headers(header -> header.frameOptions(h -> h.sameOrigin()))
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
