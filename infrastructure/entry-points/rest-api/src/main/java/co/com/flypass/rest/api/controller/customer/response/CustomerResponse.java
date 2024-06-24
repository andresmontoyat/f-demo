package co.com.flypass.rest.api.controller.customer.response;

import co.com.flypass.domain.enums.DocumentType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
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

  private UUID id;

  private DocumentType documentType;

  private String document;

  private String name;

  private String lastName;

  private String email;

  private LocalDate birthDate;

  private LocalDateTime createdDate;

}
