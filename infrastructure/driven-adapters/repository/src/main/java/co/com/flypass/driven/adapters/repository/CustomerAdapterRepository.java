package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.driven.adapters.repository.jpa.CustomerJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.mapper.CustomerJpaMapper;
import co.com.flypass.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomerAdapterRepository implements CustomerRepository {

  private final CustomerJpaRepository customerJpaRepository;
  private final CustomerJpaMapper mapper;

  public CustomerAdapterRepository(CustomerJpaRepository customerJpaRepository,
      CustomerJpaMapper mapper) {
    this.customerJpaRepository = customerJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<Customer> findByDocumentTypeAndDocument(DocumentType documentType,
      String document) {
    log.info("[START] - find customer by document: {}-{}", documentType, document);
    var result = customerJpaRepository.findByDocumentTypeAndDocument(documentType, document)
        .map(mapper::toDomain);
    log.info("[END] - find customer by document: {}-{}", documentType, document);
    return result;
  }

  @Override
  public Customer register(Customer customer) {
    log.info("[START] - register customer: {}", customer);
    var entity = mapper.toEntity(customer);
    entity = customerJpaRepository.save(entity);
    log.info("[END] - register customer: {}", entity);
    return mapper.toDomain(entity);
  }

  @Override
  public List<Customer> findAll() {
    log.info("[START] - find alls customers");
    var result = customerJpaRepository.findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
    log.info("[END] - find alls customers");
    return result;
  }

  @Override
  public Customer update(UUID id, Customer customer) {
    log.info("[START] - update customer {}", id);
    var result = customerJpaRepository.findById(id)
        .map(entity -> {
          mapper.toEntity(entity, customer);
          customerJpaRepository.save(entity);
          return mapper.toDomain(entity);
        });

    log.info("[END] - update customer: {}", id);
    return result.get();
  }

  @Override
  public void delete(UUID id) {
    log.info("[START] - delete customers {}", id);
    customerJpaRepository.deleteById(id);
    log.info("[END] - delete customers {}", id);
  }
}
