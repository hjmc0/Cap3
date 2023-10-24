package com.uob.cap3;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Bean
    // UserDetailsService userDetailsService(PasswordEncoder encoder){
    // UserDetails admin =
    // User.withUsername("admin").password(encoder.encode("pass")).roles("ADMIN").build();
    // return new InMemoryUserDetailsManager(admin);
    // }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.requestMatchers("/css/**", "/js/**", "/fonts/**", "/images/**", "/scss/**").permitAll()
                        .requestMatchers("/", "/logout", "/login").permitAll()
                        .requestMatchers("/view", "/edit/*", "/withdraw/*", "/deposit/*", "/transaction/*",
                                "/createteller", "/createaccount", "/save")
                        .authenticated())
                .formLogin(fl -> fl.loginPage("/login").successForwardUrl("/view"))
                .logout((logout) -> logout.logoutSuccessUrl("/login"))
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
