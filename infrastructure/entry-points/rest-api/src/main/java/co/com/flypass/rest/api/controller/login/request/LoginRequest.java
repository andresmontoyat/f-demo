package co.com.flypass.rest.api.controller.login.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
  @NotEmpty
  private String username;

  @NotEmpty
  private String password;
}
