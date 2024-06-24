package co.com.flypass.config;

import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig {

  private final DataSource dataSource;

  public JpaConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public AuditorAware<String> auditorProvider() {
    return () -> Optional.of("flypass");
  }
}
