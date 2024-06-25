package co.com.flypass;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.driven.adapters.repository.jpa.CustomerJpaRepository;
import co.com.flypass.rest.api.controller.customer.request.AddCustomerRequest;
import co.com.flypass.rest.api.controller.login.request.LoginRequest;
import co.com.flypass.rest.api.controller.login.response.LoginResponse;
import co.com.flypass.rest.api.controller.movement.request.DepositMovementRequest;
import co.com.flypass.rest.api.controller.product.request.AddCheckingProductRequest;
import co.com.flypass.rest.api.controller.product.request.CustomerRequest;
import co.com.flypass.rest.api.controller.product.response.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class MovementIntegrationTest {

  private final Faker faker = Faker.instance();

  @Container
  static PostgreSQLContainer postgresContainer;

  @Autowired
  private CustomerJpaRepository customerJpaRepository;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  static {
    postgresContainer = new PostgreSQLContainer("postgres:13")
        .withDatabaseName("vitxo")
        .withUsername("vitxo")
        .withPassword("vitxo");
  }

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @BeforeEach
  void setUp() {
    postgresContainer.start();
  }

  @Test
  void sendDeposit() throws Exception {
    AddCustomerRequest customerRequest = AddCustomerRequest.builder()
        .documentType(DocumentType.CC)
        .document("1234567890")
        .name("Customer")
        .lastName("Flypass")
        .email("flypass@gmail.com")
        .birthDate(LocalDate.now().minusYears(30))
        .build();

    MvcResult tokenResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(LoginRequest.builder()
                .username("admin")
                .password("admin")
                .build())))
        .andExpect(status().isOk())
        .andReturn();

    LoginResponse loginResponse = objectMapper.readValue(
        tokenResult.getResponse().getContentAsString(), LoginResponse.class);

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/customers")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customerRequest)))
        .andExpect(status().isCreated())
        .andReturn();

    AddCheckingProductRequest addCheckingProductRequest = new AddCheckingProductRequest();
    addCheckingProductRequest.setGmf(Boolean.TRUE);
    addCheckingProductRequest.setBalance(BigDecimal.TEN);
    addCheckingProductRequest.setStatus(ProductStatus.ACTIVE);
    addCheckingProductRequest.setCustomer(CustomerRequest.builder()
        .document(customerRequest.getDocument())
        .documentType(customerRequest.getDocumentType())
        .build());

    var addCheckingProductResult= mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/products")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(addCheckingProductRequest)))
        .andExpect(status().isCreated())
        .andReturn();

    ProductResponse productResponse = objectMapper.readValue(
        addCheckingProductResult.getResponse().getContentAsString(), ProductResponse.class);

    DepositMovementRequest depositMovementRequest = new DepositMovementRequest();
    depositMovementRequest.setAmount(BigDecimal.TEN);
    depositMovementRequest.setNumber(productResponse.getNumber());

    mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/movements")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(depositMovementRequest)))
        .andExpect(status().isOk())
        .andReturn();
  }
}
