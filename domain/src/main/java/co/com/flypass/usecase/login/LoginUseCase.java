package co.com.flypass.usecase.login;

import co.com.flypass.service.LoginService;

public class LoginUseCase {
  private final LoginService loginService;

  public LoginUseCase(LoginService loginService) {
    this.loginService = loginService;
  }

 public String execute(String username, String password) {
    return loginService.login(username, password);
  }
}
