package co.com.flypass.driven.adapters.repository.jpa;

import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.driven.adapters.repository.jpa.entity.CustomerEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
  Optional<CustomerEntity> findByDocumentTypeAndDocument(DocumentType documentType, String document);
}
