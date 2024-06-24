package co.com.flypass.rest.api.controller.product.request;

import co.com.flypass.domain.enums.DocumentType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

  @NotNull
  private DocumentType documentType;

  @NotEmpty
  @Pattern(regexp = "^[A-Z0-9]{4,12}$")
  private String document;
}
