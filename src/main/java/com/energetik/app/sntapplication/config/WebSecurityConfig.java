package com.energetik.app.sntapplication.config;


import com.energetik.app.sntapplication.repository.GardenerRepository;
import com.energetik.app.sntapplication.entity.Gardener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final GardenerRepository gardenerRepository;
    private final SuccessUserHandler successUserHandler;

    @Autowired
    public WebSecurityConfig(GardenerRepository gardenerRepository,
                             SuccessUserHandler successUserHandler) {
        this.gardenerRepository = gardenerRepository;
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/admin", "/user").hasRole("ADMIN")
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login.permitAll()
                        .successHandler(successUserHandler))
                .logout(logout -> logout.logoutUrl("/logout"));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Optional<Gardener> optionalUser = gardenerRepository.findGardenerByUsername(username);
            if (optionalUser.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(optionalUser.get().getUsername())
                    .password(optionalUser.get().getPassword())
                    .authorities(optionalUser.get().getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRolename()))
                            .collect(Collectors.toList()))
                    .build();
        };
    }
}
