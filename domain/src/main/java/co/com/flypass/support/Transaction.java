package co.com.flypass.support;

public interface Transaction<R> {
  R run(TransactionCallback<R> callback);
}
