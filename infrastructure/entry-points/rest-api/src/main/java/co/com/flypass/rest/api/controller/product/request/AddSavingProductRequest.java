package co.com.flypass.rest.api.controller.product.request;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.domain.enums.ProductType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddSavingProductRequest extends AddProductBaseRequest {

  @NotNull
  @Digits(integer = 10, fraction = 2)
  @Min(value = 1)
  private BigDecimal balance;

  @Override
  public ProductType getType() {
    return ProductType.SAVINGS;
  }

  @Override
  public ProductStatus getStatus() {
    return ProductStatus.ACTIVE;
  }
}
