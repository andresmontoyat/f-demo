package co.com.flypass.rest.api.controller.customer.request;

import co.com.flypass.domain.enums.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCustomerRequest {

  @NotNull
  private DocumentType documentType;

  @NotEmpty
  @Pattern(regexp = "^[A-Z0-9]{4,12}$")
  private String document;

  @NotEmpty
  @Size(min = 2, max = 30)
  private String name;

  @NotEmpty
  @Size(min = 2, max = 30)
  private String lastName;

  @NotEmpty
  @Email
  @Size(min = 8, max = 280)
  private String email;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate birthDate;

  @Schema(hidden = true)
  @AssertTrue
  public boolean isBirthDateValid() {
    if (birthDate == null) {
      return false;
    }

    var period = Period.between(birthDate, LocalDate.now()).getYears();
    return period >= 18;
  }
}
