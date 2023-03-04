package sg.edu.ntu.cart_api.service;

import org.springframework.stereotype.Service;
import sg.edu.ntu.cart_api.Exceptions.UserNotFoundException;
import sg.edu.ntu.cart_api.entity.Users;

import java.util.Optional;

@Service
public class UserService {

    public static Users unwrapUser(Optional<Users> user, Integer id) {
        if(user.isEmpty()) {
            throw new UserNotFoundException("The user with Id " + id + " cannot be found!");
        }
        return user.get();
    }
}
