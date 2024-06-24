package co.com.flypass.rest.api.controller.movement;

import co.com.flypass.domain.Movement;
import co.com.flypass.domain.enums.MovementType;
import co.com.flypass.rest.api.controller.movement.mapper.MovementRestMapper;
import co.com.flypass.rest.api.controller.movement.request.AddMovementRequest;
import co.com.flypass.rest.api.controller.movement.request.DepositMovementRequest;
import co.com.flypass.rest.api.controller.movement.request.TransferMovementRequest;
import co.com.flypass.rest.api.controller.movement.request.WithdrawalMovementRequest;
import co.com.flypass.rest.api.controller.movement.response.MovementResponse;
import co.com.flypass.usecase.movement.MovementFactoryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movements")
public class MovementController {

  private final MovementFactoryUseCase movementFactoryUseCase;
  private final MovementRestMapper mapper;

  public MovementController(MovementFactoryUseCase movementFactoryUseCase,
      MovementRestMapper mapper) {
    this.movementFactoryUseCase = movementFactoryUseCase;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<MovementResponse> add(@Validated @RequestBody AddMovementRequest request) {
    var useCase = movementFactoryUseCase.getUseCase(request.getType());
    Movement result = null;
    if (MovementType.DEPOSIT.equals(request.getType())) {
      result = useCase.execute(mapper.toMovement((DepositMovementRequest) request));
    } else if (MovementType.TRANSFER.equals(request.getType())) {
      result = useCase.execute(mapper.toMovement((TransferMovementRequest) request));
    } else {
      result= useCase.execute(mapper.toMovement((WithdrawalMovementRequest) request));
    }

    return ResponseEntity.ok(mapper.toMovementResponse(result));
  }
}
