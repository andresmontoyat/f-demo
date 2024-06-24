package co.com.flypass.rest.api.controller.customer.mapper;

import co.com.flypass.domain.Customer;
import co.com.flypass.rest.api.controller.customer.request.AddCustomerRequest;
import co.com.flypass.rest.api.controller.customer.request.UpdateCustomerRequest;
import co.com.flypass.rest.api.controller.customer.response.AddCustomerResponse;
import co.com.flypass.rest.api.controller.customer.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CustomerRestMapper {

  Customer toCustomer(AddCustomerRequest request);

  Customer toCustomer(UpdateCustomerRequest request);

  AddCustomerResponse toAddCustomerResponse(Customer customer);

  CustomerResponse toCustomerResponse(Customer customer);

}
