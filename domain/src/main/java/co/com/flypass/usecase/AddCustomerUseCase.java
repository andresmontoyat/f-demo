package co.com.flypass.usecase;

import co.com.flypass.domain.Customer;
import co.com.flypass.exception.ConflictException;
import co.com.flypass.repository.CustomerRepository;

public class AddCustomerUseCase {

  private final CustomerRepository customerRepository;

  public AddCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer execute(Customer customer) {
    var result = customerRepository.findByDocumentTypeAndDocument(customer.getDocumentType(),
        customer.getDocument());
    if (result.isPresent()) {
      throw new ConflictException("Customer already exists", customer.getDocumentType(),
          customer.getDocument());
    }

    return customerRepository.register(customer);
  }

}
