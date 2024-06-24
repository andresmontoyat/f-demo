package co.com.flypass.usecase.product;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.Product;
import co.com.flypass.repository.CustomerRepository;
import co.com.flypass.repository.ProductRepository;
import java.util.List;

public class ReadAllProductUseCase {

  private final ProductRepository productRepository;

  public ReadAllProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> execute() {
    return productRepository.findAll();
  }

}
