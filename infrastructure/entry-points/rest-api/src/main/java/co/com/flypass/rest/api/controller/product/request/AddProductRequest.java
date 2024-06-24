package co.com.flypass.rest.api.controller.product.request;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.domain.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AddCheckingProductRequest.class, name = "CHECKING"),
    @JsonSubTypes.Type(value = AddSavingProductRequest.class, name = "SAVINGS")
})
public interface AddProductRequest {

  ProductType getType();

  ProductStatus getStatus();

}
