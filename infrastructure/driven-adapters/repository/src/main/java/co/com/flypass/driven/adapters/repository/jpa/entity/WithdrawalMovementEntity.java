package co.com.flypass.driven.adapters.repository.jpa.entity;

import co.com.flypass.driven.adapters.repository.jpa.entity.support.JpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "withdrawal_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class WithdrawalMovementEntity extends JpaEntity<UUID> {

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private ProductEntity product;

  @Column(nullable = false)
  private String amount;
}
