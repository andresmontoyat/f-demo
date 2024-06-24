package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.driven.adapters.repository.jpa.ProductConsecutiveJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductConsecutiveEntity;
import co.com.flypass.repository.ProductConsecutiveRepository;
import java.math.BigInteger;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductConsecutiveAdapterRepository implements ProductConsecutiveRepository {

  private final ProductConsecutiveJpaRepository productConsecutiveJpaRepository;
  private final Map<ProductType, BigInteger> consecutives;

  public ProductConsecutiveAdapterRepository(
      ProductConsecutiveJpaRepository productConsecutiveJpaRepository,
      Map<ProductType, BigInteger> consecutives) {
    this.productConsecutiveJpaRepository = productConsecutiveJpaRepository;
    this.consecutives = consecutives;
  }

  @Override
  public synchronized BigInteger calculate(ProductType type) {

    var entity = productConsecutiveJpaRepository.findByType(type).orElseGet(() -> productConsecutiveJpaRepository.save(ProductConsecutiveEntity.builder()
        .consecutive(consecutives.get(type))
        .type(type)
        .build()));

    var consecutive = entity.getConsecutive().add(BigInteger.ONE);
    entity.setConsecutive(consecutive);
    productConsecutiveJpaRepository.save(entity);
    return consecutive;
  }
}
