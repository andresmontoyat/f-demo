package co.com.flypass.usecase.customer;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.CustomerRepository;
import co.com.flypass.repository.ProductRepository;
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
public class DeleteCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private ProductRepository productRepository;

  private DeleteCustomerUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new DeleteCustomerUseCase(customerRepository, productRepository);
  }

  @Test
  void whenCustomerExistsThenDeleteCustomer() {
    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.of(Customer.builder()
            .id(UUID.randomUUID())
            .document("12334556")
            .documentType(DocumentType.CC)
            .build()));

    Mockito.when(productRepository.findByCustomer(Mockito.any(UUID.class)))
        .thenReturn(List.of());

    Mockito.doNothing().when(customerRepository)
        .delete(Mockito.any(UUID.class));

    useCase.execute(DocumentType.CC, "123456");

    Mockito.verify(customerRepository)
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString());
    Mockito.verify(productRepository).findByCustomer(Mockito.any(UUID.class));
    Mockito.verify(customerRepository).delete(Mockito.any(UUID.class));
  }

  @Test
  void whenCustomerNotExistsThenThrowResourceNotFoundException() {
    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(
        ResourceNotFoundException.class, () -> useCase.execute(DocumentType.CC, "123456"));

    Mockito.verify(customerRepository)
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString());
  }
}
