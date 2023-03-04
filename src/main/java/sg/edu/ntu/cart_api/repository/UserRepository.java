package sg.edu.ntu.cart_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sg.edu.ntu.cart_api.entity.Users;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {
}
