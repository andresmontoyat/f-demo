package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.Product;
import co.com.flypass.driven.adapters.repository.jpa.ProductJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.mapper.ProductJpaMapper;
import co.com.flypass.repository.ProductRepository;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductAdapterRepository implements ProductRepository {

  private final ProductJpaRepository productJpaRepository;
  private final ProductJpaMapper mapper;

  public ProductAdapterRepository(ProductJpaRepository productJpaRepository,
      ProductJpaMapper mapper) {
    this.productJpaRepository = productJpaRepository;
    this.mapper = mapper;
  }

  @Override
  public List<Product> findAll() {
    log.info("[START] - find all products");
    var result = productJpaRepository.findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
    log.info("[END] - find all products");
    return result;
  }

  @Override
  public Product register(Product product) {
    log.info("[START] - register product: {}", product);
    var entity = mapper.toEntity(product);
    entity = productJpaRepository.save(entity);
    log.info("[END] - register product: {}", entity);
    return mapper.toDomain(entity);
  }

  @Override
  public Optional<Product> findByNumber(BigInteger number) {
    log.info("[START] - find product by number {}", number);
    var result = productJpaRepository.findByNumber(number)
        .map(mapper::toDomain);
    log.info("[END] - find product by number {}", number);
    return result;
  }

  @Override
  public void update(Product product) {
    productJpaRepository.findById(product.getId())
        .ifPresent(entity -> {
          mapper.toEntity(entity, product);
          productJpaRepository.save(entity);
        });
  }

  @Override
  public void delete(UUID id) {
    log.info("[START] - delete product by number {}", id);
    productJpaRepository.deleteById(id);
    log.info("[END] - delete product by number {}", id);
  }

  @Override
  public List<Product> findByCustomer(UUID customerId) {
    log.info("[START] - find all products by customer");
    var result = productJpaRepository.findAllByCustomer_Id(customerId)
        .stream()
        .map(mapper::toDomain)
        .toList();
    log.info("[END] - find all products by customer");
    return result;
  }
}
