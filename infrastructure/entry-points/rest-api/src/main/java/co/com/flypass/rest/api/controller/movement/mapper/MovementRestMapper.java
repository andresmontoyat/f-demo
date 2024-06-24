package co.com.flypass.rest.api.controller.movement.mapper;

import co.com.flypass.domain.DepositMovement;
import co.com.flypass.domain.Movement;
import co.com.flypass.domain.TransferMovement;
import co.com.flypass.domain.WithdrawalMovement;
import co.com.flypass.rest.api.controller.movement.request.DepositMovementRequest;
import co.com.flypass.rest.api.controller.movement.request.TransferMovementRequest;
import co.com.flypass.rest.api.controller.movement.request.WithdrawalMovementRequest;
import co.com.flypass.rest.api.controller.movement.response.MovementResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MovementRestMapper {

  DepositMovement toMovement(DepositMovementRequest request);

  TransferMovement toMovement(TransferMovementRequest request);

  WithdrawalMovement toMovement(WithdrawalMovementRequest request);

  MovementResponse toMovementResponse(Movement movement);
}
