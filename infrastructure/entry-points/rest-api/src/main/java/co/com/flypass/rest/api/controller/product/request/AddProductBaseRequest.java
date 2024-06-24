package co.com.flypass.rest.api.controller.product.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AddProductBaseRequest implements AddProductRequest {

  @NotNull
  private CustomerRequest customer;

  @NotNull
  private Boolean gmf;
}
