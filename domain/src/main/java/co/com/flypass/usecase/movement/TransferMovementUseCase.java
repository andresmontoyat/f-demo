package co.com.flypass.usecase.movement;

import co.com.flypass.domain.Movement;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.TransferMovement;
import co.com.flypass.domain.enums.ProductStatus;
import co.com.flypass.exception.ConflictException;
import co.com.flypass.exception.ResourceNotFoundException;
import co.com.flypass.repository.MovementRepository;
import co.com.flypass.repository.ProductRepository;
import co.com.flypass.support.Transaction;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransferMovementUseCase implements MovementUseCase<TransferMovement>{

  private final Transaction<Movement> transaction;
  private final ProductRepository productRepository;
  private final MovementRepository movementRepository;

  public TransferMovementUseCase(Transaction<Movement> transaction,
      ProductRepository productRepository, MovementRepository movementRepository) {
    this.transaction = transaction;
    this.productRepository = productRepository;
    this.movementRepository = movementRepository;
  }

  public Movement execute(TransferMovement transferMovement) {
    return transaction.run(() -> {

      if (transferMovement.getOriginNumber().equals(transferMovement.getDestinationNumber())) {
        throw new ConflictException("Origin and destination numbers cannot be the same");
      }

      var originProduct = productRepository.findByNumber(transferMovement.getOriginNumber())
          .orElseThrow(() -> new ResourceNotFoundException("Origin product not found",
              transferMovement.getOriginNumber()));

      if(!ProductStatus.ACTIVE.equals(originProduct.getStatus())) {
        throw new ConflictException("Origin product is not active", transferMovement.getOriginNumber());
      }

      if(originProduct.getBalance().compareTo(transferMovement.getAmount()) <= 0) {
        throw new ConflictException("Origin dont have balance for this transaction");
      }

      var destinationProduct = productRepository.findByNumber(transferMovement.getDestinationNumber())
          .orElseThrow(() -> new ResourceNotFoundException("Destination product not found",
              transferMovement.getDestinationNumber()));

      if(!ProductStatus.ACTIVE.equals(destinationProduct.getStatus())) {
        throw new ConflictException("Destination product is not active", transferMovement.getOriginNumber());
      }

      var originBalance = originProduct.getBalance().subtract(transferMovement.getAmount());
      var destinationBalance = destinationProduct.getBalance().add(transferMovement.getAmount());

      originProduct.setBalance(originBalance);
      destinationProduct.setBalance(destinationBalance);

      productRepository.update(originProduct);
      productRepository.update(destinationProduct);

      return  movementRepository.save(transferMovement, originProduct, destinationProduct);
    });

  }
}
