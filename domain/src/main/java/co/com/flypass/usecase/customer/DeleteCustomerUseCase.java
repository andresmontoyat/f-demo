package co.com.flypass.usecase.customer;

import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.CustomerRepository;

public class DeleteCustomerUseCase {

  private final CustomerRepository customerRepository;

  public DeleteCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void execute(DocumentType documentType, String document) {
    var result = customerRepository.findByDocumentTypeAndDocument(documentType, document)
        .orElseThrow(
            () -> new ResourceNotFoundException("Customer not found", documentType, document));

    customerRepository.delete(result.getId());
  }

}
