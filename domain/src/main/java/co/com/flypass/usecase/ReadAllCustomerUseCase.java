package co.com.flypass.usecase;

import co.com.flypass.domain.Customer;
import co.com.flypass.repository.CustomerRepository;
import java.util.List;

public class ReadAllCustomerUseCase {

  private final CustomerRepository customerRepository;

  public ReadAllCustomerUseCase(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> execute() {
    return customerRepository.findAll();
  }

}
