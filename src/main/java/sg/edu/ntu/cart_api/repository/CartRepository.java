package sg.edu.ntu.cart_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edu.ntu.cart_api.entity.Cart;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {
    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);
    Optional<Cart> findByProductId(Integer productId);
    Optional<List<Cart>> findByUserId(Integer userid);
}
