package co.com.flypass.driven.adapters.repository.jpa;

import co.com.flypass.driven.adapters.repository.jpa.entity.TransferMovementEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferMovementJpaRepository extends JpaRepository<TransferMovementEntity, UUID> {

}
