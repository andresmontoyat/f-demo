package co.com.flypass.usecase.product;

import co.com.flypass.exception.ConflictException;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.ProductRepository;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DeleteProductUseCase {

  private final ProductRepository productRepository;

  public DeleteProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void execute(BigInteger number) {
    var product = productRepository.findByNumber(number)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found", number));

    if (!product.getBalance().equals(BigDecimal.ZERO)) {
      throw new ConflictException("Product has balance", number, product.getBalance());
    }

    productRepository.delete(product.getId());
  }

}
