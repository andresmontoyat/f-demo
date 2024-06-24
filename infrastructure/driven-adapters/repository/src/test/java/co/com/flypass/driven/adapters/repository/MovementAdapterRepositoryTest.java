package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.DepositMovement;
import co.com.flypass.domain.Movement;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.TransferMovement;
import co.com.flypass.domain.WithdrawalMovement;
import co.com.flypass.driven.adapters.repository.jpa.DepositMovementJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.TransferMovementJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.WithdrawalMovementJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.entity.DepositMovementEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.TransferMovementEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.WithdrawalMovementEntity;
import co.com.flypass.driven.adapters.repository.jpa.mapper.MovementJpaMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovementAdapterRepositoryTest {

  @Mock
  private TransferMovementJpaRepository transferMovementJpaRepository;
  @Mock
  private DepositMovementJpaRepository depositMovementJpaRepository;
  @Mock
  private WithdrawalMovementJpaRepository withdrawalMovementJpaRepository;
  @Mock
  private MovementJpaMapper mapper;

  private MovementAdapterRepository adapterRepository;

  @BeforeEach
  void setUp() {
    adapterRepository = new MovementAdapterRepository(depositMovementJpaRepository,
        transferMovementJpaRepository, withdrawalMovementJpaRepository, mapper);
  }

  @Test
  void saveDeposit() {
    var deposit = DepositMovement.builder().
        build();
    var product = Product.builder()
        .build();

    Mockito.when(mapper.toEntity(Mockito.any(DepositMovement.class)))
        .thenReturn(DepositMovementEntity.builder().build());
    Mockito.when(mapper.toEntity(Mockito.any(Product.class)))
        .thenReturn(ProductEntity.builder().build());
    Mockito.when(depositMovementJpaRepository.save(Mockito.any(DepositMovementEntity.class)))
        .thenReturn(DepositMovementEntity.builder().build());
    Mockito.when(mapper.toDomain(Mockito.any(DepositMovementEntity.class)))
        .thenReturn(Movement.builder()
            .build());

    var result = adapterRepository.save(deposit, product);
    Assertions.assertNotNull(result);

    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(DepositMovement.class));
    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(Product.class));
    Mockito.verify(depositMovementJpaRepository, Mockito.times(1)).save(Mockito.any(DepositMovementEntity.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(DepositMovementEntity.class));
  }

  @Test
  void saveTransfer() {
    var transferMovement = TransferMovement.builder().
        build();
    var originProduct = Product.builder()
        .build();
    var destinationProduct = Product.builder()
        .build();

    Mockito.when(mapper.toEntity(Mockito.any(TransferMovement.class)))
        .thenReturn(TransferMovementEntity.builder().build());
    Mockito.when(mapper.toEntity(Mockito.any(Product.class)))
        .thenReturn(ProductEntity.builder().build());
    Mockito.when(transferMovementJpaRepository.save(Mockito.any(TransferMovementEntity.class)))
        .thenReturn(TransferMovementEntity.builder().build());
    Mockito.when(mapper.toDomain(Mockito.any(TransferMovementEntity.class)))
        .thenReturn(Movement.builder()
            .build());

    var result = adapterRepository.save(transferMovement, originProduct, destinationProduct);
    Assertions.assertNotNull(result);

    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(TransferMovement.class));
    Mockito.verify(mapper, Mockito.times(2)).toEntity(Mockito.any(Product.class));
    Mockito.verify(transferMovementJpaRepository, Mockito.times(1)).save(Mockito.any(TransferMovementEntity.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(TransferMovementEntity.class));
  }

  @Test
  void saveWithdrawal() {
    var withdrawalMovement = WithdrawalMovement.builder().
        build();
    var product = Product.builder()
        .build();

    Mockito.when(mapper.toEntity(Mockito.any(WithdrawalMovement.class)))
        .thenReturn(WithdrawalMovementEntity.builder().build());
    Mockito.when(mapper.toEntity(Mockito.any(Product.class)))
        .thenReturn(ProductEntity.builder().build());
    Mockito.when(withdrawalMovementJpaRepository.save(Mockito.any(WithdrawalMovementEntity.class)))
        .thenReturn(WithdrawalMovementEntity.builder().build());
    Mockito.when(mapper.toDomain(Mockito.any(WithdrawalMovementEntity.class)))
        .thenReturn(Movement.builder()
            .build());

    var result = adapterRepository.save(withdrawalMovement, product);
    Assertions.assertNotNull(result);

    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(WithdrawalMovement.class));
    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(Product.class));
    Mockito.verify(withdrawalMovementJpaRepository, Mockito.times(1)).save(Mockito.any(WithdrawalMovementEntity.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(WithdrawalMovementEntity.class));
  }
}
