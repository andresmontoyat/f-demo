package co.com.flypass.driven.adapters.repository;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.driven.adapters.repository.jpa.CustomerJpaRepository;
import co.com.flypass.driven.adapters.repository.jpa.entity.CustomerEntity;
import co.com.flypass.driven.adapters.repository.jpa.mapper.CustomerJpaMapper;
import com.github.javafaker.Faker;
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
public class CustomerAdapterRepositoryTest {
  private final Faker faker = Faker.instance();

  @Mock
  private CustomerJpaRepository customerJpaRepository;

  @Mock
  private CustomerJpaMapper mapper;

  private CustomerAdapterRepository adapterRepository;

  @BeforeEach
  void setUp() {
    adapterRepository = new CustomerAdapterRepository(customerJpaRepository, mapper);
  }

  @Test
  void findByDocumentTypeAndDocument() {
    var customerId = UUID.randomUUID();
    var documentType = DocumentType.CC;
    var document = "12345678990";

    Mockito.when(
            customerJpaRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
                Mockito.anyString()))
        .thenReturn(Optional.of(CustomerEntity.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .build()));

    Mockito.when(mapper.toDomain(Mockito.any(CustomerEntity.class)))
        .thenReturn(Customer.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .build());

    var result = adapterRepository.findByDocumentTypeAndDocument(documentType, document);
    Assertions.assertNotNull(result);
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(customerId, result.get().getId());
    Assertions.assertEquals(document, result.get().getDocument());
    Assertions.assertEquals(documentType, result.get().getDocumentType());

    Mockito.verify(customerJpaRepository, Mockito.times(1))
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString());
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(CustomerEntity.class));
  }

  @Test
  void register() {
    var customerId = UUID.randomUUID();
    var documentType = DocumentType.CC;
    var document = "12345678990";

    Mockito.when(mapper.toEntity(Mockito.any(Customer.class)))
        .thenReturn(CustomerEntity.builder()
            .document(document)
            .documentType(documentType)
            .build());

    Mockito.when(customerJpaRepository.save(Mockito.any(CustomerEntity.class)))
        .thenReturn(CustomerEntity.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .build());

    Mockito.when(mapper.toDomain(Mockito.any(CustomerEntity.class)))
        .thenReturn(Customer.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .build());

    var result = adapterRepository.register(Customer.builder()
        .document(document)
        .documentType(documentType)
        .build());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(customerId, result.getId());

    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(Customer.class));
    Mockito.verify(customerJpaRepository, Mockito.times(1)).save(Mockito.any(CustomerEntity.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(CustomerEntity.class));
  }

  @Test
  void findAll() {
    var customerId = UUID.randomUUID();
    var documentType = DocumentType.CC;
    var document = "12345678990";

    Mockito.when(customerJpaRepository.findAll())
        .thenReturn(java.util.List.of(CustomerEntity.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .build()));

    Mockito.when(mapper.toDomain(Mockito.any(CustomerEntity.class)))
        .thenReturn(Customer.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .build());

    var result = adapterRepository.findAll();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.size());
    Assertions.assertEquals(customerId, result.get(0).getId());

    Mockito.verify(customerJpaRepository, Mockito.times(1)).findAll();
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(CustomerEntity.class));
  }

  @Test
  void update() {
    var customerId = UUID.randomUUID();
    var documentType = DocumentType.CC;
    var document = "12345678990";
    var oldName = faker.harryPotter().character();
    var newName = faker.harryPotter().character();

    Mockito.when(customerJpaRepository.findById(Mockito.any(UUID.class)))
            .thenReturn(Optional.of(CustomerEntity.builder()
                .id(customerId)
                .document(document)
                .documentType(documentType)
                    .name(oldName)
                .build()));

    Mockito.doNothing().when(mapper)
        .toEntity(Mockito.any(CustomerEntity.class), Mockito.any(Customer.class));

    Mockito.when(customerJpaRepository.save(Mockito.any(CustomerEntity.class)))
        .thenReturn(CustomerEntity.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .name(newName)
            .build());

    Mockito.when(mapper.toDomain(Mockito.any(CustomerEntity.class)))
        .thenReturn(Customer.builder()
            .id(customerId)
            .document(document)
            .documentType(documentType)
            .name(newName)
            .build());

    var result = adapterRepository.update(customerId, Customer.builder()
        .document(document)
        .documentType(documentType)

        .build());

    Assertions.assertNotNull(result);
    Assertions.assertEquals(customerId, result.getId());
    Assertions.assertEquals(document, result.getDocument());
    Assertions.assertEquals(documentType, result.getDocumentType());
    Assertions.assertEquals(newName, result.getName());

    Mockito.verify(customerJpaRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
    Mockito.verify(mapper, Mockito.times(1)).toEntity(Mockito.any(CustomerEntity.class), Mockito.any(Customer.class));
    Mockito.verify(customerJpaRepository, Mockito.times(1)).save(Mockito.any(CustomerEntity.class));
    Mockito.verify(mapper, Mockito.times(1)).toDomain(Mockito.any(CustomerEntity.class));

  }

  @Test
  void delete() {
    var customerId = UUID.randomUUID();

    Mockito.doNothing().when(customerJpaRepository)
        .deleteById(Mockito.any(UUID.class));

    adapterRepository.delete(customerId);

    Mockito.verify(customerJpaRepository, Mockito.times(1)).deleteById(Mockito.any(UUID.class));
  }
}
