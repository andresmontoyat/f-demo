package co.com.flypass.driven.adapters.repository.jpa;

import co.com.flypass.driven.adapters.repository.jpa.entity.DepositMovementEntity;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductEntity;
import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositMovementJpaRepository extends JpaRepository<DepositMovementEntity, UUID> {

}
