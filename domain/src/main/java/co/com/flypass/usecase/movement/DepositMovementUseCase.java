package co.com.flypass.usecase.movement;

import co.com.flypass.domain.DepositMovement;
import co.com.flypass.domain.Movement;
import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.exception.ConflictException;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.MovementRepository;
import co.com.flypass.repository.ProductRepository;
import co.com.flypass.support.Transaction;

public class DepositMovementUseCase implements MovementUseCase<DepositMovement>{

  private final Transaction<Movement> transaction;
  private final ProductRepository productRepository;
  private final MovementRepository movementRepository;

  public DepositMovementUseCase(Transaction<Movement> transaction,
      ProductRepository productRepository,
      MovementRepository movementRepository) {
    this.transaction = transaction;
    this.productRepository = productRepository;
    this.movementRepository = movementRepository;
  }

  public Movement execute(DepositMovement depositMovement) {
    return transaction.run(() -> {
      var product = productRepository.findByNumber(depositMovement.getNumber())
          .orElseThrow(() -> new ResourceNotFoundException("Product not found",
              depositMovement.getNumber()));

      if (!ProductStatus.ACTIVE.equals(product.getStatus())) {
        throw new ConflictException("Product is not active", depositMovement.getNumber());
      }

      var balance = product.getBalance().add(depositMovement.getAmount());
      product.setBalance(balance);
      productRepository.update(product);

      return movementRepository.save(depositMovement, product);
    });
  }
}
