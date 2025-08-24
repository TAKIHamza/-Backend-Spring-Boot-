package estm.stage.ifsane.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import estm.stage.ifsane.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    

  
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) 
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/utilisateurs/login", "/error").permitAll() 
                                .requestMatchers("/api/personnes/*").hasRole("ADMIN") 
                                .requestMatchers("/api/membres/*").hasRole("ADMIN")
                                .requestMatchers("/api/zones/*").hasRole("ADMIN")
                                .requestMatchers("/api/factures/pay/{id}").hasRole("USER")
                                .requestMatchers("/api/factures/paid").hasAnyRole("ADMIN", "USER") 
                                .requestMatchers("/api/factures/{id}").hasAnyRole("ADMIN", "USER") 
                                .requestMatchers("/api/factures/*").hasRole("ADMIN")
                                .requestMatchers("/api/compteurs/*").hasRole("ADMIN")
                                .requestMatchers("/api/compteurs/").hasAnyRole("ADMIN", "RESPO")
                                .requestMatchers("/api/compteurs/zone/{zoneName}").hasAnyRole("ADMIN", "RESPO")
                                .requestMatchers("/api/compteurs/list").hasAnyRole("ADMIN", "RESPO")
                                .requestMatchers("/api/utilisateurs/*").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // Ensure JWT is used for authentication
                .httpBasic(basic -> basic.disable()); 
        return http.build();
    }
 @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Autoriser toutes les origines, vous pouvez spécifier les origines autorisées de manière spécifique si nécessaire
        config.addAllowedMethod("*"); // Autoriser toutes les méthodes HTTP
        config.addAllowedHeader("*"); // Autoriser tous les en-têtes HTTP
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/utilisateurs/login");
    }
}
