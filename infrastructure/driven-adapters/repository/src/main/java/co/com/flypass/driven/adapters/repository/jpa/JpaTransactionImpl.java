package co.com.flypass.driven.adapters.repository.jpa;

import co.com.flypass.exception.TransactionException;
import co.com.flypass.support.Transaction;
import co.com.flypass.support.TransactionCallback;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class JpaTransactionImpl<R> implements Transaction<R> {

  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SQLException.class,
      IllegalStateException.class, TransactionException.class})
  public R run(TransactionCallback<R> callback) {
    try {
      log.debug("[START] - start transaction");
      var result = callback.call();
      log.debug("[END] - end transaction");
      return result;
    } catch (Exception e) {
      log.error("An error has occurred trying to execute transaction", e);
      throw new TransactionException(e.getMessage(), e);
    }
  }
}
