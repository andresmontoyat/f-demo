package co.com.flypass.usecase.movement;

import co.com.flypass.domain.Movement;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.WithdrawalMovement;
import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.exception.ConflictException;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.MovementRepository;
import co.com.flypass.repository.ProductRepository;
import co.com.flypass.support.Transaction;
import java.util.Map;
import java.util.UUID;

public class WithdrawalMovementUseCase implements MovementUseCase<WithdrawalMovement>{

  private final Transaction<Movement> transaction;
  private final ProductRepository productRepository;
  private final MovementRepository movementRepository;

  public WithdrawalMovementUseCase(Transaction<Movement> transaction,
      ProductRepository productRepository, MovementRepository movementRepository) {
    this.transaction = transaction;
    this.productRepository = productRepository;
    this.movementRepository = movementRepository;
  }

  public Movement execute(WithdrawalMovement withdrawalMovement) {
    return transaction.run(() -> {
      var product = productRepository.findByNumber(withdrawalMovement.getNumber())
          .orElseThrow(() -> new ResourceNotFoundException("Product not found",
              withdrawalMovement.getNumber()));

      if (!ProductStatus.ACTIVE.equals(product.getStatus())) {
        throw new ConflictException("Product is not active", withdrawalMovement.getNumber());
      }

      if (product.getBalance().compareTo(withdrawalMovement.getAmount()) < 0) {
        throw new ConflictException("Product dont have balance for this transaction");
      }

      var balance = product.getBalance().subtract(withdrawalMovement.getAmount());
      product.setBalance(balance);
      productRepository.update(product);

      return movementRepository.save(withdrawalMovement, product);
    });
  }
}
