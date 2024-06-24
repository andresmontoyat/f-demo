package co.com.flypass.usecase.customer;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.CustomerRepository;

public class GetCustomerUseCase {

  private final CustomerRepository customerRepository;

  public GetCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(DocumentType documentType, String document) {
    return customerRepository.findByDocumentTypeAndDocument(documentType, document)
        .orElseThrow(
            () -> new ResourceNotFoundException("Customer not found", documentType, document));
  }

}
