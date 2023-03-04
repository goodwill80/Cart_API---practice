package sg.edu.ntu.cart_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edu.ntu.cart_api.entity.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);

}
