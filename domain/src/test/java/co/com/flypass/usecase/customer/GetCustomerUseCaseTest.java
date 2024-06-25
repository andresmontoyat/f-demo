package co.com.flypass.usecase.customer;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.repository.CustomerRepository;
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
public class GetCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  private GetCustomerUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new GetCustomerUseCase(customerRepository);
  }

  @Test
  void whenCustomerExistsThenGetCustomer() {
    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.of(Customer.builder()
            .id(UUID.randomUUID())
            .document("12334556")
            .documentType(DocumentType.CC)
            .build()));

    var result = useCase.execute(DocumentType.CC, "12334556");
    Assertions.assertNotNull(result);

    Mockito.verify(customerRepository)
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class), Mockito.anyString());
  }

}

