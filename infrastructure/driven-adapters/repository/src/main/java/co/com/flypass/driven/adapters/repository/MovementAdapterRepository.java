package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.DepositMovement;
import co.com.flypass.domain.Movement;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.TransferMovement;
import co.com.flypass.domain.WithdrawalMovement;
import co.com.flypass.driven.adapters.repository.jpa.DepositMovementJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.TransferMovementJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.WithdrawalMovementJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.mapper.MovementJpaMapper;
import co.com.flypass.repository.MovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovementAdapterRepository implements MovementRepository {

  private final DepositMovementJpaRepository depositMovementJpaRepository;
  private final TransferMovementJpaRepository transferMovementJpaRepository;
  private final WithdrawalMovementJpaRepository withdrawalMovementJpaRepository;
  private final MovementJpaMapper mapper;

  public MovementAdapterRepository(DepositMovementJpaRepository depositMovementJpaRepository,
      TransferMovementJpaRepository transferMovementJpaRepository,
      WithdrawalMovementJpaRepository withdrawalMovementJpaRepository, MovementJpaMapper mapper) {
    this.depositMovementJpaRepository = depositMovementJpaRepository;
    this.transferMovementJpaRepository = transferMovementJpaRepository;
    this.withdrawalMovementJpaRepository = withdrawalMovementJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Movement save(DepositMovement depositMovement, Product product) {
    log.info("[START] - register deposit movement: {}", depositMovement);
    var entity = mapper.toEntity(depositMovement);
    entity.setProduct(mapper.toEntity(product));
    entity = depositMovementJpaRepository.save(entity);
    log.info("[END] - register deposit movement: {}", entity);
    return mapper.toDomain(entity);
  }

  @Override
  public Movement save(TransferMovement transferMovement, Product originProduct,
      Product destinationProduct) {
    log.info("[START] - register transfer movement: {}", transferMovement);
    var entity = mapper.toEntity(transferMovement);
    entity.setOriginProduct(mapper.toEntity(originProduct));
    entity.setDestinationProduct(mapper.toEntity(destinationProduct));
    entity = transferMovementJpaRepository.save(entity);
    log.info("[END] - register transfer movement: {}", entity);
    return mapper.toDomain(entity);
  }

  @Override
  public Movement save(WithdrawalMovement transferMovement, Product product) {
    log.info("[START] - register withdrawal movement: {}", transferMovement);
    var entity = mapper.toEntity(transferMovement);
    entity.setProduct(mapper.toEntity(product));
    entity = withdrawalMovementJpaRepository.save(entity);
    log.info("[END] - register withdrawal movement: {}", entity);
    return mapper.toDomain(entity);
  }
}
