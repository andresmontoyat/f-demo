package co.com.flypass.usecase.customer;

import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.exception.ConflictException;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.CustomerRepository;
import co.com.flypass.repository.ProductRepository;
import java.util.Objects;

public class DeleteCustomerUseCase {

  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;

  public DeleteCustomerUseCase(CustomerRepository customerRepository,
      ProductRepository productRepository) {
    this.customerRepository = customerRepository;
    this.productRepository = productRepository;
  }

  public void execute(DocumentType documentType, String document) {
    var result = customerRepository.findByDocumentTypeAndDocument(documentType, document)
        .orElseThrow(
            () -> new ResourceNotFoundException("Customer not found", documentType, document));

    var products = productRepository.findByCustomer(result.getId());
    if (!products.isEmpty()) {
      throw new ConflictException("Customer has products", documentType, document);
    }

    customerRepository.delete(result.getId());
  }

}
