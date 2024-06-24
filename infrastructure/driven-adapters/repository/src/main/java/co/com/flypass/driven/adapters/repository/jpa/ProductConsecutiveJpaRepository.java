package co.com.flypass.driven.adapters.repository.jpa;

import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductConsecutiveEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductConsecutiveJpaRepository extends
    JpaRepository<ProductConsecutiveEntity, UUID> {

  Optional<ProductConsecutiveEntity> findByType(ProductType type);
}
