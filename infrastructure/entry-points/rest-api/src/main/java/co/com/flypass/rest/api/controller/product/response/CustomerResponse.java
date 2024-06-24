package co.com.flypass.rest.api.controller.product.response;

import co.com.flypass.domain.enums.DocumentType;
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
public class CustomerResponse {

  private DocumentType documentType;

  private String document;

  private String name;

  private String lastName;
}
