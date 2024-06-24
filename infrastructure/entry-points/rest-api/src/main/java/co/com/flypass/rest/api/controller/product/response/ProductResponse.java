package co.com.flypass.rest.api.controller.product.response;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.domain.enums.ProductType;
import java.math.BigDecimal;
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
public class ProductResponse {

  private UUID id;

  private ProductType type;

  private ProductStatus status;

  private String number;

  private boolean gmf;

  private BigDecimal balance;

  private LocalDateTime createdDate;
}
