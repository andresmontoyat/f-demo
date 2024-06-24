package co.com.flypass.usecase.product;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.ProductRepository;
import java.math.BigInteger;

public class ChangeStatusProductUseCase {

  private final ProductRepository productRepository;

  public ChangeStatusProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void execute(BigInteger number, ProductStatus status) {
    var product = productRepository.findByNumber(number)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found", number));

    product.setStatus(status);
    productRepository.update(product);
  }

}
