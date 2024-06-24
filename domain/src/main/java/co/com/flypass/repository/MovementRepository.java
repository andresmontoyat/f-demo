package co.com.flypass.repository;

import co.com.flypass.domain.DepositMovement;
import co.com.flypass.domain.Movement;
import co.com.flypass.domain.Product;
import co.com.flypass.domain.TransferMovement;
import co.com.flypass.domain.WithdrawalMovement;

public interface MovementRepository {

  Movement save(DepositMovement depositMovement, Product product);

  Movement save(TransferMovement transferMovement, Product originProduct, Product destinationProduct);

  Movement save(WithdrawalMovement withdrawalMovement, Product product);
}
