package co.com.flypass.repository;

import co.com.flypass.domain.Product;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

  List<Product> findAll();

  Product register(Product product);

  Optional<Product> findByNumber(BigInteger number);

  void update( Product product);

  void delete(UUID id);
}
