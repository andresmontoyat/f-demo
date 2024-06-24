package co.com.flypass.driven.adapters.repository.jpa.entity;

import co.com.flypass.domain.enums.ProductType;
import co.com.flypass.driven.adapters.repository.jpa.entity.support.JpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "products_consecutives")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class ProductConsecutiveEntity extends JpaEntity<UUID> {

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private ProductType type;

  @Column(nullable = false, length = 10)
  private BigInteger consecutive;
}
