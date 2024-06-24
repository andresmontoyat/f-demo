package co.com.flypass.rest.api.controller.product.mapper;

import co.com.flypass.domain.Product;
import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.rest.api.controller.product.request.AddCheckingProductRequest;
import co.com.flypass.rest.api.controller.product.request.AddProductRequest;
import co.com.flypass.rest.api.controller.product.request.AddSavingProductRequest;
import co.com.flypass.rest.api.controller.product.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductRestMapper {

  ProductResponse toProductResponse(Product product);

  default Product resolveProduct(AddProductRequest request) {
    return ProductType.CHECKING.equals(request.getType())
        ? toProduct((AddCheckingProductRequest) request)
        : toProduct((AddSavingProductRequest) request);
  }

  Product toProduct(AddCheckingProductRequest request);

  Product toProduct(AddSavingProductRequest request);
}
