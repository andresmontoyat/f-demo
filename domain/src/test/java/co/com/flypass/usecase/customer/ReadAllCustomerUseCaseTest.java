package co.com.flypass.usecase.customer;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.repository.CustomerRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReadAllCustomerUseCaseTest {

  @Mock
  private CustomerRepository customerRepository;

  private ReadAllCustomerUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new ReadAllCustomerUseCase(customerRepository);
  }

  @Test
  void whenOneCustomerExistsThenGetCustomersList() {
    Mockito.when(customerRepository.findAll())
        .thenReturn(List.of(Customer.builder()
            .id(UUID.randomUUID())
            .document("12334556")
            .documentType(DocumentType.CC)
            .build()));

    var result = useCase.execute();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.size());

    Mockito.verify(customerRepository)
       .findAll();
  }

}

