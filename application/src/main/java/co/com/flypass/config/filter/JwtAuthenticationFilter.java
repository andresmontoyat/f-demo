package co.com.flypass.config.filter;

import co.com.flypass.driven.adapters.security.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var header = request.getHeader("Authorization");
    if (header != null) {

      try {
        String username = jwtUtil.extractUsername(header);
        var userDetails = userDetailsService.loadUserByUsername(username);
        var authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null,
            userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

      } catch (Exception e) {
        throw new AuthenticationServiceException("Authentication failure", e);
      }
    }

    filterChain.doFilter(request, response);
  }
}
