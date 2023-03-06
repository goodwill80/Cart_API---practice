package sg.edu.ntu.cart_api.webController;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.MediaPrintableArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sg.edu.ntu.cart_api.DataObjects.CartItem;
import sg.edu.ntu.cart_api.DataObjects.CartSummary;
import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.repository.CartRepository;
import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sg.edu.ntu.cart_api.service.ProductService;



@RestController
@RequestMapping("/api/cart")
public class CartController {

    record message(String message) {};

    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepo;

    @Autowired
    ProductService productService;

    @Autowired
    private ProductRepository productRepo;

    @GetMapping
    public ResponseEntity<CartSummary> getAllCartItemsOfUser(@RequestParam Integer userid) {
        return new ResponseEntity<>(cartService.showCartSummaryOfUser(userid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> updateCart(@RequestBody CartItem cartItem) {
        cartService.updateCart(cartItem);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

 
    // FROM SCTP LESSON ****
    @GetMapping("/all")
    public ResponseEntity<List<Cart>> list(){
        List<Cart> cartRecords = (List<Cart>)cartRepo.findAll();

        if(cartRecords.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(cartRecords);
    }

    @PostMapping("/add/{productid}")
    public ResponseEntity<Object> addOneToCart(@PathVariable Integer productid, @RequestParam(required = false) Integer quantity) {
        Optional<Cart> findCart = cartRepo.findByProductId(productid);
        try {
        // Cart is present
        if(findCart.isPresent()) {
                Cart cartFound = findCart.get();
                int qty = quantity == null || quantity == 0 ? 1 : quantity;
                cartFound.setQuantity(cartFound.getQuantity() + qty);
                cartRepo.save(cartFound);
                return ResponseEntity.ok().body(cartFound);
        }
        // Cart not Present
        Cart cart = new Cart();
            Product product = ProductService.unwrapProduct(productRepo.findById(productid), productid);
            cart.setProduct(product);
            cart.setQuantity(1);
            cartRepo.save(cart);
            return ResponseEntity.ok().body(cart);
        } catch(Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new message("Something went wrong!"));
        }

    }
    
    @PostMapping("/deduct/{productid}")
    public ResponseEntity<Object> deductOneFromCart(@PathVariable Integer productid) {
        Optional<Cart> findCart = cartRepo.findByProductId(productid);
        if(findCart.isPresent()) {
            try {
                Cart cartFound = findCart.get();
                int qty = cartFound.getQuantity() == 1 ? 0 : 1;
                if(qty == 0) {
                    cartRepo.delete(cartFound);
                    return ResponseEntity.ok().body(new message("Cart has been deleted"));
                }
                cartFound.setQuantity(cartFound.getQuantity() - qty);
                return ResponseEntity.ok().body(cartRepo.save(cartFound));
            } catch(Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.badRequest().body(new message("Something went wrong!"));
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Object> clearAll() {
        try {
            cartRepo.deleteAll();
            return ResponseEntity.ok().body(new message("All cart items has been deleted"));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new message("Something went wrong!"));
        }
    }


    // ONLY for TESTING
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


    // EDISON's METHOD

    // @RequestMapping(value="/add/{productId}", method = RequestMethod.POST)
    // public ResponseEntity add(@RequestParam Optional<Integer> quantity, @PathVariable int productId){

    //     // Check if product id exist in product table
    //     Optional<Product> optionalProduct = productRepo.findById(productId);
    //     if(optionalProduct.isPresent()){
            
    //         // Check if productId exist in the cart table       
    //         Optional<Cart> optionalCart = cartRepo.findByProductId(productId);
    //         if(optionalCart.isPresent()){
    //             Cart cartItem = optionalCart.get();
    //             int currentQuantity = cartItem.getQuantity();
    //             cartItem.setQuantity(quantity.isPresent() ? quantity.get() : cartItem.getQuantity() + 1);
    //             cartRepo.save(cartItem);
    //         }else{
    //             // create a new record in cart and set quantity to 1
    //             Cart newCartItem = new Cart();
    //             newCartItem.setQuantity(1);
    //             newCartItem.setProduct(optionalProduct.get()); // this optional product exist, and the id value is filled.
    //             cartRepo.save(newCartItem);
    //         }    
    //     }

    //     return ResponseEntity.badRequest().build();
    // }



}




