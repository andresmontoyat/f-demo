package co.com.flypass.driven.adapters.repository.jpa.entity.support;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

  @CreatedBy
  @Column(name = "created_by")
  protected String createdBy;

  @CreatedDate
  @Column(name = "created_date")
  @Temporal(TemporalType.TIMESTAMP)
  protected LocalDateTime createdDate;

  @LastModifiedBy
  @Column(name = "modified_by")
  protected String modifiedBy;

  @LastModifiedDate
  @Column(name = "last_modified_date")
  @Temporal(TemporalType.TIMESTAMP)
  protected LocalDateTime lastModifiedDate;


}
