package co.com.flypass.usecase.customer;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.exception.ConflictException;
import co.com.flypass.repository.CustomerRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  private AddCustomerUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new AddCustomerUseCase(customerRepository);
  }

  @Test
  void whenCustomerExistThenThroeException() {

    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.of(Customer.builder()
            .document("12334556")
            .documentType(DocumentType.CC)
            .build()));

    var result = Assertions.assertThrows(ConflictException.class,
        () -> useCase.execute(Customer.builder()
            .document("12334556")
            .documentType(DocumentType.CC)
            .build()));

    Assertions.assertNotNull(result);
    Assertions.assertEquals("Customer already exists",
        result.getMessage());

    Mockito.verify(customerRepository)
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class), Mockito.anyString());

  }

  @Test
  void whenCustomerExistTheSaveCustomer() {
    var customer = Customer.builder()
        .document("12334556")
        .documentType(DocumentType.CC)
        .build();

    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.empty());

    Mockito.when(customerRepository.register(Mockito.any(Customer.class)))
        .thenReturn(customer);

    var result = useCase.execute(customer);

    Assertions.assertNotNull(result);

    Mockito.verify(customerRepository)
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class), Mockito.anyString());
    Mockito.verify(customerRepository)
        .register(Mockito.any(Customer.class));
  }

}
