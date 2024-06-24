package co.com.flypass.config;

import co.com.flypass.config.filter.JwtAuthenticationFilter;
import co.com.flypass.driven.adapters.security.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import jakarta.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public RSAKey rsaKey() throws JOSEException {
    return new RSAKeyGenerator(2048)
        .keyID("123")
        .generate();
  }

  @Bean
  public SecurityFilterChain web(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
    http
        .csrf((csrf) -> csrf
            .ignoringRequestMatchers("/api/v1/*")
        )
        .cors(Customizer.withDefaults())
        .authorizeHttpRequests((authorize) -> authorize
            .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
            .requestMatchers("/api/v1/login").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails user = User. withUsername("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder);

    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
