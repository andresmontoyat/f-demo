package co.com.flypass.rest.api.controller.product;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.repository.CustomerRepository;
import co.com.flypass.repository.ProductConsecutiveRepository;
import co.com.flypass.repository.ProductRepository;
import co.com.flypass.rest.api.controller.product.mapper.ProductRestMapper;
import co.com.flypass.rest.api.controller.product.request.AddCheckingProductRequest;
import co.com.flypass.rest.api.controller.product.request.AddProductRequest;
import co.com.flypass.rest.api.controller.product.response.ProductResponse;
import co.com.flypass.support.Transaction;
import co.com.flypass.support.TransactionCallback;
import co.com.flypass.usecase.product.AddProductUseCase;
import co.com.flypass.usecase.product.ChangeStatusProductUseCase;
import co.com.flypass.usecase.product.DeleteProductUseCase;
import co.com.flypass.usecase.product.GetProductUseCase;
import co.com.flypass.usecase.product.ReadAllProductUseCase;
import java.math.BigDecimal;
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
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private Transaction<Product> transaction;

  @Mock
  private ProductConsecutiveRepository productConsecutiveRepository;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private ProductRestMapper mapper;

  private ProductController productController;

  @BeforeEach
  public void setUp() {
    productController = new ProductController(
        new ReadAllProductUseCase(productRepository),
        new AddProductUseCase(transaction, productRepository, productConsecutiveRepository,
            customerRepository),
        new ChangeStatusProductUseCase(productRepository),
        new DeleteProductUseCase(productRepository),
        new GetProductUseCase(productRepository), mapper);
  }

  @Test
  void findAll() {
    Mockito.when(productRepository.findAll())
        .thenReturn(List.of(Product.builder().build()));
    Mockito.when(mapper.toProductResponse(Mockito.any(Product.class)))
        .thenReturn(ProductResponse.builder().build());

    var result = productController.findAll();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(1, result.getBody().size());
    Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

    Mockito.verify(productRepository, Mockito.times(1)).findAll();
    Mockito.verify(mapper, Mockito.times(1)).toProductResponse(Mockito.any(Product.class));
  }

  @Test
  void create() {
    Mockito.when(mapper.resolveProduct(Mockito.any(AddProductRequest.class)))
        .thenReturn(Product.builder()
            .type(ProductType.CHECKING)
            .customer(Customer.builder()
                .document("123456789")
                .documentType(DocumentType.CC)
                .build())
            .build());

    Mockito.doAnswer(invocation -> {
      TransactionCallback<Product> callback = invocation.getArgument(0);
      return callback.call();
    }).when(transaction).run(Mockito.any());

    Mockito.when(customerRepository.findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
            Mockito.anyString()))
        .thenReturn(Optional.of(Customer.builder()
            .build()));
    Mockito.when(productConsecutiveRepository.calculate(Mockito.any(ProductType.class)))
        .thenReturn(BigInteger.TWO);

    Mockito.when(productRepository.register(Mockito.any(Product.class)))
        .thenReturn(Product.builder()
            .build());

    Mockito.when(mapper.toProductResponse(Mockito.any(Product.class)))
        .thenReturn(ProductResponse.builder().build());

    var result = productController.create(new AddCheckingProductRequest());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());

    Mockito.verify(mapper, Mockito.times(1)).resolveProduct(Mockito.any(AddProductRequest.class));
    Mockito.verify(transaction, Mockito.times(1)).run(Mockito.any());
    Mockito.verify(customerRepository, Mockito.times(1)).findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
        Mockito.anyString());

    Mockito.verify(customerRepository, Mockito.times(1)).findByDocumentTypeAndDocument(Mockito.any(DocumentType.class),
        Mockito.anyString());
    Mockito.verify(productConsecutiveRepository, Mockito.times(1)).calculate(Mockito.any(ProductType.class));
    Mockito.verify(productRepository, Mockito.times(1)).register(Mockito.any(Product.class));
    Mockito.verify(mapper, Mockito.times(1)).toProductResponse(Mockito.any(Product.class));
  }

  @Test
  void changeStatus() {
    Mockito.when(productRepository.findByNumber(Mockito.any(BigInteger.class)))
        .thenReturn(Optional.of(Product.builder()
                .status(ProductStatus.INACTIVE)
                .balance(BigDecimal.TEN)
            .build()));

    Mockito.doNothing().when(productRepository)
        .update(Mockito.any(Product.class));

    var result = productController.changeStatus(BigInteger.ONE, ProductStatus.ACTIVE);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

    Mockito.verify(productRepository, Mockito.times(1)).findByNumber(Mockito.any(BigInteger.class));
    Mockito.verify(productRepository, Mockito.times(1)).update(Mockito.any(Product.class));

  }

  @Test
  void delete() {
    Mockito.when(productRepository.findByNumber(Mockito.any(BigInteger.class)))
        .thenReturn(Optional.of(Product.builder()
                .id(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
            .build()));

    Mockito.doNothing().when(productRepository)
        .delete(Mockito.any(UUID.class));

    var result = productController.delete(BigInteger.ONE);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    Mockito.verify(productRepository, Mockito.times(1)).findByNumber(Mockito.any(BigInteger.class));
    Mockito.verify(productRepository, Mockito.times(1)).delete(Mockito.any(UUID.class));
  }

  @Test
  void findByNumber() {
    Mockito.when(productRepository.findByNumber(Mockito.any(BigInteger.class)))
        .thenReturn(Optional.of(Product.builder()
                .id(UUID.randomUUID())
                .status(ProductStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
            .build()));

    Mockito.when(mapper.toProductResponse(Mockito.any(Product.class)))
        .thenReturn(ProductResponse.builder().build());

    var result = productController.findByNumber(BigInteger.ONE);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

    Mockito.verify(productRepository, Mockito.times(1)).findByNumber(Mockito.any(BigInteger.class));
    Mockito.verify(mapper, Mockito.times(1)).toProductResponse(Mockito.any(Product.class));
  }
}
