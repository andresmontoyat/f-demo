package co.com.flypass.usecase;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.CustomerRepository;

public class UpdateCustomerUseCase {

  private final CustomerRepository customerRepository;

  public UpdateCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(DocumentType documentType, String document, Customer customer) {
    var result = customerRepository.findByDocumentTypeAndDocument(documentType, document)
        .orElseThrow(
            () -> new ResourceNotFoundException("Customer not found", documentType, document));

    return customerRepository.update(result.getId(), customer);
  }

}
