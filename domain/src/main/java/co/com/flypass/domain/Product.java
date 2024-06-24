package co.com.flypass.domain;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.domain.enums.ProductType;
import java.math.BigDecimal;
import java.math.BigInteger;
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
public class Product {

  private UUID id;

  private Customer customer;

  private ProductType type;

  private ProductStatus status;

  private BigInteger number;

  private boolean gmf;

  private BigDecimal balance;

  private LocalDateTime createdDate;
}
