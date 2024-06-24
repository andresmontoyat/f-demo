package co.com.flypass.rest.api.controller.product;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.rest.api.controller.product.mapper.ProductRestMapper;
import co.com.flypass.rest.api.controller.product.request.AddProductRequest;
import co.com.flypass.rest.api.controller.product.response.ProductResponse;
import co.com.flypass.usecase.product.AddProductUseCase;
import co.com.flypass.usecase.product.ChangeStatusProductUseCase;
import co.com.flypass.usecase.product.DeleteProductUseCase;
import co.com.flypass.usecase.product.GetProductUseCase;
import co.com.flypass.usecase.product.ReadAllProductUseCase;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ReadAllProductUseCase readAllProductUseCase;
  private final AddProductUseCase addProductUseCase;
  private final ChangeStatusProductUseCase changeStatusProductUseCase;
  private final DeleteProductUseCase deleteProductUseCase;
  private final GetProductUseCase getProductUseCase;
  private final ProductRestMapper mapper;

  public ProductController(ReadAllProductUseCase readAllProductUseCase,
      AddProductUseCase addProductUseCase, ChangeStatusProductUseCase changeStatusProductUseCase,
      DeleteProductUseCase deleteProductUseCase, GetProductUseCase getProductUseCase,
      ProductRestMapper mapper) {
    this.readAllProductUseCase = readAllProductUseCase;
    this.addProductUseCase = addProductUseCase;
    this.changeStatusProductUseCase = changeStatusProductUseCase;
    this.deleteProductUseCase = deleteProductUseCase;
    this.getProductUseCase = getProductUseCase;
    this.mapper = mapper;
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAll() {
    var result = readAllProductUseCase.execute();
    return ResponseEntity.ok(result.stream().map(mapper::toProductResponse).toList());
  }

  @PostMapping
  public ResponseEntity<ProductResponse> create(@Validated @RequestBody AddProductRequest request) {
    var customer = addProductUseCase.execute(mapper.resolveProduct(request));
    return ResponseEntity.created(URI.create(String.format("/%s", customer.getId()))).body(mapper.toProductResponse(customer));
  }

  @PatchMapping("/{product}/status/{status}")
  public ResponseEntity<Void> changeStatus(@PathVariable("product") BigInteger product,
      @PathVariable("status") ProductStatus status) {
    changeStatusProductUseCase.execute(product, status);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{product}")
  public ResponseEntity<Void> delete(@PathVariable("product") BigInteger product) {
    deleteProductUseCase.execute(product);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{product}")
  public ResponseEntity<ProductResponse> findByNumber(@PathVariable("product") BigInteger product) {
    var result = getProductUseCase.execute(product);
    return ResponseEntity.ok(mapper.toProductResponse(result));
  }
}
