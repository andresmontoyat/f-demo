package co.com.flypass.driven.adapters.repository.jpa.mapper;

import co.com.flypass.domain.DepositMovement;
import co.com.flypass.domain.Movement;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.TransferMovement;
import co.com.flypass.domain.WithdrawalMovement;
import co.com.flypass.driven.adapters.repository.jpa.entity.DepositMovementEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.TransferMovementEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.WithdrawalMovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MovementJpaMapper {

  DepositMovementEntity toEntity(DepositMovement depositMovement);

  ProductEntity toEntity(Product product);

  TransferMovementEntity toEntity(TransferMovement transferMovement);

  WithdrawalMovementEntity toEntity(WithdrawalMovement withdrawalMovement);

  Movement toDomain(DepositMovementEntity entity);

  Movement toDomain(TransferMovementEntity entity);

  Movement toDomain(WithdrawalMovementEntity entity);
}
