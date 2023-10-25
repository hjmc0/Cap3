package com.uob.cap3;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/css/**", "/js/**", "/fonts/**", "/images/**", "/scss/**").permitAll()
                        .requestMatchers("/", "/logout", "/login").permitAll()
                        .requestMatchers("/view", "/edit/*","/saveEdit", "/withdraw/*", "/deposit/*", "/transact/*","/transaction/*",
                                "/createteller", "/createaccount", "/savetransact","/adding","/addaccount", "/delete/*", "/close", "/deleteteller/*", "/deleteteller")
                        .authenticated()
                        .requestMatchers("/**").anonymous())
                .formLogin(fl -> fl.loginPage("/login").successForwardUrl("/view"))
                .logout((logout) -> logout.logoutSuccessUrl("/"))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    UserDetailsService tellerDetailsService() {
        return new TellerDetailsServiceImpl();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(tellerDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
