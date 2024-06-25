package co.com.flypass.driven.adapters.security;

import co.com.flypass.driven.adapters.security.util.JwtUtil;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class JwtSecurityAdapterServiceTest {

  @Mock
  private AuthenticationManager authenticationManager;

  private JwtSecurityAdapterService jwtSecurityAdapterService;

  @BeforeEach
  void setUp() throws Exception {

    RSAKey rsaKey = new RSAKeyGenerator(2048)
        .keyID("flypass")
        .generate();

    jwtSecurityAdapterService = new JwtSecurityAdapterService(authenticationManager,
        new JwtUtil(rsaKey));
  }

  @Test
  void login() throws JOSEException {
    Mockito.when(authenticationManager.authenticate(Mockito.any(Authentication.class)))
        .thenReturn(new UsernamePasswordAuthenticationToken("admin", "admin"));

    var token = jwtSecurityAdapterService.login("admin", "admin");
    Assertions.assertNotNull(token);

    Mockito.verify(authenticationManager).authenticate(Mockito.any(Authentication.class));
  }
}
