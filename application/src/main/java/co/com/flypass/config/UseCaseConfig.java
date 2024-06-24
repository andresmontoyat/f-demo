package co.com.flypass.config;

import co.com.flypass.domain.enums.ProductType;
import java.math.BigInteger;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    basePackages = {"co.com.flypass.usecase"},
    includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[UseCase]")}, useDefaultFilters = false)
public class UseCaseConfig {

  @Bean(name = "consecutives")
  public Map<ProductType, BigInteger> consecutives() {
    return Map.of(ProductType.CHECKING, BigInteger.valueOf(3300000000L),
        ProductType.SAVINGS, BigInteger.valueOf(5300000000L));
  }

}
