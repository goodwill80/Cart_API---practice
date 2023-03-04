package sg.edu.ntu.cart_api.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.ntu.cart_api.DataObjects.CartItem;
import sg.edu.ntu.cart_api.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping
    public ResponseEntity<Object> getAllCartItemsOfUser(@RequestParam Integer userid) {
       return null;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> updateCart(@RequestBody CartItem cartItem) {
        cartService.updateCart(cartItem);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
