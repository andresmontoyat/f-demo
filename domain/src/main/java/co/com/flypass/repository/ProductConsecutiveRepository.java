package co.com.flypass.repository;

import co.com.flypass.domain.enums.ProductType;
import java.math.BigInteger;

public interface ProductConsecutiveRepository {

  BigInteger calculate(ProductType type);
}
