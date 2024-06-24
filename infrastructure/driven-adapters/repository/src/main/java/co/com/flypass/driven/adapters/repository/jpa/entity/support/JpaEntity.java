package co.com.flypass.driven.adapters.repository.jpa.entity.support;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@MappedSuperclass
public abstract class JpaEntity<ID extends Serializable> extends AuditEntity implements Serializable, Persistable<ID> {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  protected ID id;

  @Override
  public boolean isNew() {
    return id == null;
  }
}
