package co.com.flypass.driven.adapters.repository.jpa.mapper;

import co.com.flypass.domain.Customer;
import co.com.flypass.driven.adapters.repository.jpa.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CustomerJpaMapper {

  Customer toDomain(CustomerEntity entity);

  CustomerEntity toEntity(Customer domain);

  void toEntity(@MappingTarget CustomerEntity entity, Customer domain);
}
