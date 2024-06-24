package co.com.flypass.driven.adapters.repository.jpa.mapper;

import co.com.flypass.domain.Product;
import co.com.flypass.driven.adapters.repository.jpa.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductJpaMapper {

  Product toDomain(ProductEntity entity);

  ProductEntity toEntity(Product domain);

  void toEntity(@MappingTarget ProductEntity entity, Product domain);
}
