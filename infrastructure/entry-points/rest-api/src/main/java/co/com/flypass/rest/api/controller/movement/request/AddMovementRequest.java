package co.com.flypass.rest.api.controller.movement.request;

import co.com.flypass.domain.enums.MovementType;
import co.com.flypass.rest.api.controller.product.request.AddCheckingProductRequest;
import co.com.flypass.rest.api.controller.product.request.AddSavingProductRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DepositMovementRequest.class, name = "DEPOSIT"),
    @JsonSubTypes.Type(value = WithdrawalMovementRequest.class, name = "WITHDRAWAL"),
    @JsonSubTypes.Type(value = TransferMovementRequest.class, name = "TRANSFER")
})
public interface AddMovementRequest {

  MovementType getType();
}
