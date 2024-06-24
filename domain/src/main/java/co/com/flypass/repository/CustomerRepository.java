package co.com.flypass.repository;

import co.com.flypass.domain.Customer;
import co.com.flypass.domain.enums.DocumentType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

  Optional<Customer> findByDocumentTypeAndDocument(DocumentType documentType,
      String document);

  Customer register(Customer customer);

  List<Customer> findAll();

  Customer update(UUID id, Customer customer);

  void delete(UUID id);
}
