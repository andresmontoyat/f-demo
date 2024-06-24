package co.com.flypass.usecase.product;

import co.com.flypass.domain.Product;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.CustomerRepository;
import co.com.flypass.repository.ProductConsecutiveRepository;
import co.com.flypass.repository.ProductRepository;
import co.com.flypass.support.Transaction;

public class AddProductUseCase {

  private final Transaction<Product> transaction;
  private final ProductRepository productRepository;
  private final ProductConsecutiveRepository productConsecutiveRepository;
  private final CustomerRepository customerRepository;

  public AddProductUseCase(Transaction<Product> transaction, ProductRepository productRepository,
      ProductConsecutiveRepository productConsecutiveRepository,
      CustomerRepository customerRepository) {
    this.transaction = transaction;
    this.productRepository = productRepository;
    this.productConsecutiveRepository = productConsecutiveRepository;
    this.customerRepository = customerRepository;
  }

  public Product execute(Product product) {
    return transaction.run(() -> {
      var customer = customerRepository.findByDocumentTypeAndDocument(
              product.getCustomer().getDocumentType(), product.getCustomer().getDocument())
          .orElseThrow(
              () -> new ResourceNotFoundException("Customer not found",
                  product.getCustomer().getDocumentType(), product.getCustomer().getDocument()));

      var consecutive = productConsecutiveRepository.calculate(product.getType());
      product.setNumber(consecutive);
      product.setCustomer(customer);

      return productRepository.register(product);
    });
  }

}
