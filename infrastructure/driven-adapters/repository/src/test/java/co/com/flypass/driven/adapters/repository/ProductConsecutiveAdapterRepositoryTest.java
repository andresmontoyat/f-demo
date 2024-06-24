package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.driven.adapters.repository.jpa.ProductConsecutiveJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductConsecutiveEntity;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductConsecutiveAdapterRepositoryTest {

  @Mock
  private ProductConsecutiveJpaRepository productConsecutiveJpaRepository;
  private ProductConsecutiveAdapterRepository adapterRepository;
  private final Map<ProductType, BigInteger> consecutives = Map.of(ProductType.CHECKING, BigInteger.ONE,
      ProductType.SAVINGS, BigInteger.TEN);

  @BeforeEach
  void setUp() {
    adapterRepository = new ProductConsecutiveAdapterRepository(productConsecutiveJpaRepository,
        consecutives);
  }

  @Test
  void calculate() {
    Mockito.when(productConsecutiveJpaRepository.findByType(ProductType.CHECKING))
        .thenReturn(Optional.empty());

    Mockito.when(productConsecutiveJpaRepository.save(Mockito.any(ProductConsecutiveEntity.class)))
        .thenReturn(ProductConsecutiveEntity.builder()
            .id(UUID.randomUUID())
            .consecutive(consecutives.get(ProductType.CHECKING))
            .type(ProductType.CHECKING)
            .build());

    var result = adapterRepository.calculate(ProductType.CHECKING);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(BigInteger.valueOf(2L), result);

    Mockito.verify(productConsecutiveJpaRepository, Mockito.times(1)).findByType(ProductType.CHECKING);
    Mockito.verify(productConsecutiveJpaRepository, Mockito.times(2)).save(Mockito.any(ProductConsecutiveEntity.class));
  }

}
