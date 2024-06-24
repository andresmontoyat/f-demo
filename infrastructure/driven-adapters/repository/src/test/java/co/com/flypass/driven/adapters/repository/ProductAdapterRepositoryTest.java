package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.Product;
import co.com.flypass.driven.adapters.repository.jpa.ProductJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductEntity;
import co.com.flypass.driven.adapters.repository.jpa.mapper.ProductJpaMapper;
import java.math.BigInteger;
import java.util.List;
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
public class ProductAdapterRepositoryTest {

  @Mock
  private ProductJpaRepository productJpaRepository;

  @Mock
  private ProductJpaMapper mapper;

  private ProductAdapterRepository adapterRepository;

  @BeforeEach
  void setUp() {
    adapterRepository = new ProductAdapterRepository(productJpaRepository, mapper);
  }

  @Test
  void findAll() {
    Mockito.when(productJpaRepository.findAll())
        .thenReturn(List.of(ProductEntity.builder()
            .build()));
    Mockito.when(mapper.toDomain(Mockito.any(ProductEntity.class)))
        .thenReturn(Product.builder()
            .build());

    var result = adapterRepository.findAll();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.size());

    Mockito.verify(productJpaRepository, Mockito.times(1)).findAll();
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(ProductEntity.class));
  }

  @Test
  void register() {
    var product = Product.builder()
        .build();

    Mockito.when(mapper.toEntity(Mockito.any(Product.class)))
        .thenReturn(ProductEntity.builder()
            .build());

    Mockito.when(productJpaRepository.save(Mockito.any(ProductEntity.class)))
        .thenReturn(ProductEntity.builder()
            .build());

    Mockito.when(mapper.toDomain(Mockito.any(ProductEntity.class)))
        .thenReturn(Product.builder()
            .build());

    var result = adapterRepository.register(product);
    Assertions.assertNotNull(result);

    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(Product.class));
    Mockito.verify(productJpaRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(ProductEntity.class));
  }

  @Test
  void findByNumber() {
    var number = BigInteger.ONE;

    Mockito.when(productJpaRepository.findByNumber(Mockito.any(BigInteger.class)))
        .thenReturn(Optional.of(ProductEntity.builder()
            .number(number)
            .build()));

    Mockito.when(mapper.toDomain(Mockito.any(ProductEntity.class)))
        .thenReturn(Product.builder()
            .number(number)
            .build());

    var result = adapterRepository.findByNumber(number);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(number, result.get().getNumber());

    Mockito.verify(productJpaRepository, Mockito.times(1))
        .findByNumber(Mockito.any(BigInteger.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(ProductEntity.class));
  }

  @Test
  void update() {
    var id = UUID.randomUUID();
    var number = BigInteger.ONE;
    var product = Product.builder()
        .id(id)
        .number(number)
        .build();

    Mockito.when(productJpaRepository.findById(Mockito.any(UUID.class)))
        .thenReturn(Optional.of(ProductEntity.builder()
            .id(id)
            .number(number)
            .build()));

    Mockito.doNothing().when(mapper)
        .toEntity(Mockito.any(ProductEntity.class), Mockito.any(Product.class));

    Mockito.when(productJpaRepository.save(Mockito.any(ProductEntity.class)))
        .thenReturn(ProductEntity.builder()
            .id(id)
            .number(number)
            .build());

    adapterRepository.update(product);

    Mockito.verify(productJpaRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(ProductEntity.class), Mockito.any(Product.class));
    Mockito.verify(productJpaRepository, Mockito.times(1)).save(Mockito.any(ProductEntity.class));
  }

  @Test
  void delete() {
    var id = UUID.randomUUID();

    Mockito.doNothing().when(productJpaRepository)
       .deleteById(Mockito.any(UUID.class));

    adapterRepository.delete(id);

    Mockito.verify(productJpaRepository, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
  }

  @Test
  void findByCustomer() {
    var customerId = UUID.randomUUID();

    Mockito.when(productJpaRepository.findAllByCustomer_Id(Mockito.any(UUID.class)))
       .thenReturn(List.of(ProductEntity.builder()
           .build()));

    Mockito.when(mapper.toDomain(Mockito.any(ProductEntity.class)))
       .thenReturn(Product.builder()
           .build());

    var result = adapterRepository.findByCustomer(customerId);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.size());

    Mockito.verify(productJpaRepository, Mockito.times(1)).findAllByCustomer_Id(Mockito.any(UUID.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(ProductEntity.class));
  }
}
