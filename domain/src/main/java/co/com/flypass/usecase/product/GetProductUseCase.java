package co.com.flypass.usecase.product;

import co.com.flypass.domain.Product;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.ProductRepository;
import java.math.BigInteger;

public class GetProductUseCase {

  private final ProductRepository productRepository;

  public GetProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(BigInteger number) {
    return productRepository.findByNumber(number)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found", number));
  }

}
