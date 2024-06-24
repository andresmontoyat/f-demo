package co.com.flypass.driven.adapters.repository.jpa.entity;

import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.driven.adapters.repository.jpa.entity.support.JpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "number"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductEntity extends JpaEntity<UUID> {

  @ManyToOne(optional = false)
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private CustomerEntity customer;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private ProductType type;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private ProductStatus status;

  @Column(nullable = false, length = 10)
  private BigInteger number;

  @Column(nullable = false)
  private boolean gmf;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal balance;
}
