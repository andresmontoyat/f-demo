package co.com.flypass.rest.api.controller.login;

import co.com.flypass.rest.api.controller.login.request.LoginRequest;
import co.com.flypass.rest.api.controller.login.response.LoginResponse;
import co.com.flypass.usecase.login.LoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class AuthController {

  private final LoginUseCase loginUseCase;

  public AuthController(LoginUseCase loginUseCase) {
    this.loginUseCase = loginUseCase;
  }

  @PostMapping
  public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
    var token = loginUseCase.execute(request.getUsername(), request.getPassword());
    return ResponseEntity.ok(LoginResponse.builder()
            .token(token)
        .build());
  }
}
