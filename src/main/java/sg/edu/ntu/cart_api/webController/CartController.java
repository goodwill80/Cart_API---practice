package sg.edu.ntu.cart_api.webController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.ntu.cart_api.DataObjects.CartItem;
import sg.edu.ntu.cart_api.DataObjects.CartSummary;
import sg.edu.ntu.cart_api.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping
    public ResponseEntity<CartSummary> getAllCartItemsOfUser(@RequestParam Integer userid) {
        return new ResponseEntity<>(cartService.showCartSummaryOfUser(userid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> updateCart(@RequestBody CartItem cartItem) {
        cartService.updateCart(cartItem);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/addone")
    public ResponseEntity<HttpStatus> addOne(@RequestParam Integer userid, @RequestParam Integer productid) {
        cartService.incrementByOne(userid, productid);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/minusone")
    public ResponseEntity<HttpStatus> subtractOne(@RequestParam Integer userid, @RequestParam Integer productid) {
        cartService.decrementByOne(userid, productid);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
