package sg.edu.ntu.cart_api.repository;

import org.springframework.data.repository.CrudRepository;
import sg.edu.ntu.cart_api.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
