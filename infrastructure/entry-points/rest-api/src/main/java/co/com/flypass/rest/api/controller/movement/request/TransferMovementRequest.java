package co.com.flypass.rest.api.controller.movement.request;

import co.com.flypass.domain.enums.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferMovementRequest implements AddMovementRequest{

  @NotNull
  @Positive
  private BigInteger originNumber;

  @NotNull
  @Positive
  private BigInteger destinationNumber;

  @NotNull
  @Min(1)
  @Positive
  private BigDecimal amount;

  @Override
  public MovementType getType() {
    return MovementType.TRANSFER;
  }
}
