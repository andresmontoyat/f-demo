package co.com.flypass.usecase.movement;

import co.com.flypass.domain.enums.MovementType;
import java.util.HashMap;
import java.util.Map;

public class MovementFactoryUseCase {

  private final Map<MovementType, MovementUseCase> useCaseMap = new HashMap<>();

  public MovementFactoryUseCase(DepositMovementUseCase depositMovementUseCase,
      TransferMovementUseCase transferMovementUseCase,
      WithdrawalMovementUseCase withdrawalMovementUseCase) {
    useCaseMap.put(MovementType.DEPOSIT, depositMovementUseCase);
    useCaseMap.put(MovementType.TRANSFER, transferMovementUseCase);
    useCaseMap.put(MovementType.WITHDRAWAL, withdrawalMovementUseCase);
  }

  public MovementUseCase getUseCase(MovementType type) {
    MovementUseCase useCase = useCaseMap.get(type);
    if (useCase == null) {
      throw new IllegalArgumentException("Invalid movement type: " + type);
    }
    return useCase;
  }
}
