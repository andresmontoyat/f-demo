package co.com.flypass.usecase.product;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.repository.CustomerRepository;
import co.com.flypass.repository.ProductConsecutiveRepository;
import co.com.flypass.repository.ProductRepository;
import co.com.flypass.support.Transaction;
import co.com.flypass.support.TransactionCallback;
import java.math.BigInteger;
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
public class AddProductUseCaseTest {

  @Mock
  private Transaction<Product> transaction;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private ProductConsecutiveRepository productConsecutiveRepository;
  @Mock
  private CustomerRepository customerRepository;

  private AddProductUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new AddProductUseCase(transaction, productRepository, productConsecutiveRepository,
        customerRepository);
  }

  @Test
  void addProduct() {
    Mockito.doAnswer(invocation -> {
      TransactionCallback<Product> callback = invocation.getArgument(0);
      return callback.call();
    }).when(transaction).run(Mockito.any());

    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.of(Customer.builder()
            .id(UUID.randomUUID())
            .document("12334556")
            .documentType(DocumentType.CC)
            .build()));

    Mockito.when(productConsecutiveRepository.calculate(Mockito.any(ProductType.class)))
        .thenReturn(BigInteger.TWO);

    Mockito.when(productRepository.register(Mockito.any(Product.class)))
        .thenReturn(Product.builder()
            .build());

    var result = useCase.execute(Product.builder()
        .type(ProductType.CHECKING)
        .customer(Customer.builder()
            .document("12334556")
            .documentType(DocumentType.CC)
            .build())
        .build());
    Assertions.assertNotNull(result);

    Mockito.verify(transaction).run(Mockito.any());
    Mockito.verify(customerRepository)
        .findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString());
    Mockito.verify(productConsecutiveRepository).calculate(Mockito.any(ProductType.class));
    Mockito.verify(productRepository).register(Mockito.any(Product.class));
  }
}
