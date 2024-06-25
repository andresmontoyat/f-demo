package co.com.flypass.driven.adapters.security;

import co.com.flypass.driven.adapters.security.util.JwtUtil;
import co.com.flypass.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtSecurityAdapterService implements LoginService {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  public JwtSecurityAdapterService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public String login(String username, String password) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
      return jwtUtil.generateToken(username);
    } catch (Exception e) {
      throw new IllegalStateException("Authentication failed");
    }
  }
}
