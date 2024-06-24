package co.com.flypass.driven.adapters.repository.jpa.entity;

import co.com.flypass.domain.enums.DocumentType;
import co.com.flypass.driven.adapters.repository.jpa.entity.support.JpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customers",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"documentType", "document"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class CustomerEntity extends JpaEntity<UUID> {

  @Column(nullable = false, length = 30)
  private String name;

  @Column(nullable = false, length = 30)
  private String lastName;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private DocumentType documentType;

  @Column(nullable = false, length = 20)
  private String document;

  @Column(nullable = false, length = 280)
  private String email;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private LocalDate birthDate;
}
