package co.com.flypass.rest.api.controller.customer;

import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.rest.api.controller.customer.mapper.CustomerRestMapper;
import co.com.flypass.rest.api.controller.customer.request.AddCustomerRequest;
import co.com.flypass.rest.api.controller.customer.request.UpdateCustomerRequest;
import co.com.flypass.rest.api.controller.customer.response.AddCustomerResponse;
import co.com.flypass.rest.api.controller.customer.response.CustomerResponse;
import co.com.flypass.usecase.customer.AddCustomerUseCase;
import co.com.flypass.usecase.customer.DeleteCustomerUseCase;
import co.com.flypass.usecase.customer.GetCustomerUseCase;
import co.com.flypass.usecase.customer.ReadAllCustomerUseCase;
import co.com.flypass.usecase.customer.UpdateCustomerUseCase;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

  private final AddCustomerUseCase addCustomerUseCase;
  private final ReadAllCustomerUseCase readAllCustomerUseCase;
  private final GetCustomerUseCase getCustomerUseCase;
  private final UpdateCustomerUseCase updateCustomerUseCase;
  private final DeleteCustomerUseCase deleteCustomerUseCase;
  private final CustomerRestMapper mapper;

  public CustomerController(AddCustomerUseCase addCustomerUseCase,
      ReadAllCustomerUseCase readAllCustomerUseCase, GetCustomerUseCase getCustomerUseCase,
      UpdateCustomerUseCase updateCustomerUseCase, DeleteCustomerUseCase deleteCustomerUseCase,
      CustomerRestMapper mapper) {
    this.addCustomerUseCase = addCustomerUseCase;
    this.readAllCustomerUseCase = readAllCustomerUseCase;
    this.getCustomerUseCase = getCustomerUseCase;
    this.updateCustomerUseCase = updateCustomerUseCase;
    this.deleteCustomerUseCase = deleteCustomerUseCase;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<AddCustomerResponse> create(
      @Validated @RequestBody AddCustomerRequest addCustomerRequest) {
    var customer = addCustomerUseCase.execute(mapper.toCustomer(addCustomerRequest));
    return ResponseEntity.created(URI.create(String.format("/%s", customer.getId())))
        .body(mapper.toAddCustomerResponse(customer));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponse>> findAll() {
    var result = readAllCustomerUseCase.execute();
    return ResponseEntity.ok(result.stream().map(mapper::toCustomerResponse).toList());
  }

  @GetMapping("/{documentType}/{document}")
  public ResponseEntity<CustomerResponse> findByDocument(
      @PathVariable("documentType") DocumentType documentType,
      @PathVariable("document") String document) {
    var result = getCustomerUseCase.execute(documentType, document);
    return ResponseEntity.ok(mapper.toCustomerResponse(result));
  }

  @PutMapping("/{documentType}/{document}")
  public ResponseEntity<CustomerResponse> update(
      @PathVariable("documentType") DocumentType documentType,
      @PathVariable("document") String document,
      @Validated @RequestBody UpdateCustomerRequest updateCustomerRequest) {
    var customer = updateCustomerUseCase.execute(documentType, document,
        mapper.toCustomer(updateCustomerRequest));
    return ResponseEntity.ok(mapper.toCustomerResponse(customer));
  }

  @DeleteMapping("/{documentType}/{document}")
  public ResponseEntity<Void> delete(@PathVariable("documentType") DocumentType documentType,
      @PathVariable("document") String document) {
    deleteCustomerUseCase.execute(documentType, document);
    return ResponseEntity.noContent().build();
  }
}
