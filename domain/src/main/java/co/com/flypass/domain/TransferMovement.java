package co.com.flypass.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransferMovement {
  private BigInteger originNumber;

  private BigInteger destinationNumber;

  private BigDecimal amount;
}
