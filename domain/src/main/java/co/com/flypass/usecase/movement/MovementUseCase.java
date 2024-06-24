package co.com.flypass.usecase.movement;

import co.com.flypass.domain.Movement;

public interface MovementUseCase<T> {
  Movement execute(T movement);
}
