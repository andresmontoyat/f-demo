package co.com.flypass.support;

@FunctionalInterface
public interface TransactionCallback<R> {
  R call();
}
