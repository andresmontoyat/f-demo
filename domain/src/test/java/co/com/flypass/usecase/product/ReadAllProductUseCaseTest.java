package co.com.flypass.usecase.product;

import co.com.flypass.domain.Product;
import co.com.flypass.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReadAllProductUseCaseTest {
  @Mock
  private ProductRepository productRepository;
  private ReadAllProductUseCase useCase;

  @BeforeEach
  void setUp() {
    useCase = new ReadAllProductUseCase(productRepository);
  }

  @Test
  void testExecute() {
    Mockito.when(productRepository.findAll())
            .thenReturn(List.of(Product.builder()
                .build()));
    var result = useCase.execute();
    Assertions.assertNotNull(result);

    Mockito.verify(productRepository).findAll();
  }
}
