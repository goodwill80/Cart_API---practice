package sg.edu.ntu.cart_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.ntu.cart_api.DataObjects.CartItem;
import sg.edu.ntu.cart_api.DataObjects.CartSummary;
import sg.edu.ntu.cart_api.Exceptions.CartNotFoundException;
import sg.edu.ntu.cart_api.entity.Cart;
import sg.edu.ntu.cart_api.entity.Product;
import sg.edu.ntu.cart_api.entity.Users;
import sg.edu.ntu.cart_api.repository.CartRepository;
import sg.edu.ntu.cart_api.repository.ProductRepository;
import sg.edu.ntu.cart_api.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ProductRepository productRepo;

    // Display Full Cart Summary
    public CartSummary showCartSummaryOfUser(Integer userid) {
       Optional<List<Cart>> cartsOfUser = cartRepo.findByUserId(userid);
       if(cartsOfUser.isEmpty()) {
           throw new CartNotFoundException("The cart summary belonging to userid " + userid + " not found");
       }
       List<Cart> cartsFound = cartsOfUser.get();
       int sum = 0;
       float cost = 0f;
       for(Cart cart : cartsFound) {
           sum += cart.getQuantity();
           cost += (cart.getQuantity() * cart.getProduct().getPrice());
       }
       return new CartSummary(sum, cost, cartsFound);
    }


    // Update Cart  -  Replacement method, subtraction and addition handle in client/react
    public void updateCart(CartItem cartItem) {
        Optional<Cart> findCart = cartRepo.findByUserIdAndProductId(
                cartItem.getUserid(), cartItem.getProductId()
        );
        // Check if quantity return is 0
        if(checkForZeroAndRemoveCart(findCart, cartItem) ){
            return;
        };
        // check if cart is already present
        if(findCart.isPresent()) {
            Cart cart = findCart.get();
            cart.setQuantity(cartItem.getQuantity());
            cartRepo.save(cart);
        } else {
            // else, product item is not found in cart
            Product product = ProductService.unwrapProduct(productRepo
                    .findById(cartItem.getProductId()), cartItem.getProductId());
            Users user = UserService.unwrapUser(
                    userRepo.findById(cartItem.getUserid()), cartItem.getUserid());
            Cart newCart = new Cart(cartItem.getQuantity(), product, user);
            cartRepo.save(newCart);
        }
    }

    // updateCart method 2 - Direct Add and Delete to existing or new quantity
    private void updateCart (CartItem cartItem, String actionparam) {
        Optional<Cart> findCart = cartRepo.findByUserIdAndProductId(
                cartItem.getUserid(), cartItem.getProductId()
        );
       // For existing cart records
       if(findCart.isPresent()) {
           Cart cart = actionToUpdate(findCart.get(), cartItem, actionparam);
           if(cart.getQuantity() <= 0) {
               cartRepo.delete(findCart.get());
           }
           cartRepo.save(cart);
       }

       // For new cart records
        Product product = ProductService.unwrapProduct(productRepo
                .findById(cartItem.getProductId()), cartItem.getProductId());
        Users user = UserService.unwrapUser(
                userRepo.findById(cartItem.getUserid()), cartItem.getUserid());
        Cart newCart = new Cart(0, product, user);
        Cart updatedNewCart = actionToUpdate(newCart, cartItem, actionparam);
        if(updatedNewCart.getQuantity() == 0) return;
        cartRepo.save(updatedNewCart);
    }

    // Update helper
    private Cart actionToUpdate(Cart target, CartItem dto, String action) {
        if(action.equalsIgnoreCase("add")) {
            target.setQuantity(target.getQuantity() + dto.getQuantity());
            return target;
        }

        target.setQuantity(target.getQuantity() - dto.getQuantity());
        return target;
    }

    // Remove cart from table if the update quantity is zero
    private boolean checkForZeroAndRemoveCart(Optional<Cart> cart, CartItem cartItem) {
        if(cartItem.getQuantity() <= 0) {
            cartRepo.delete(unwrapCart(cart, cartItem.getProductId()));
            return true;
        }
        return false;
    }

    // Check for Null exception
    public static Cart unwrapCart(Optional<Cart> cart, Integer id) {
        if(cart.isEmpty()) {
            throw new CartNotFoundException("The cart with product Id " + id + " cannot be found!");
        }
        return cart.get();
    }

    // Optional Methods for testing

    // Add One
    public void incrementByOne(Integer userid, Integer productId) {
        Optional<Cart> findCart = cartRepo.findByUserIdAndProductId(userid, productId);
        if(findCart.isEmpty()) {
            Product product = ProductService
                    .unwrapProduct(productRepo.findById(productId), productId);
            Users user = UserService.unwrapUser(userRepo.findById(userid), userid);
            Cart newCart = new Cart(1, product, user);
            cartRepo.save(newCart);
        } else {
            Cart found = findCart.get();
            found.setQuantity(findCart.get().getQuantity() + 1);
            cartRepo.save(found);
        }
    }

    // Subtract one
    public void decrementByOne(Integer userid, Integer productId) {
        Cart cart = unwrapCart(cartRepo.findByUserIdAndProductId(userid, productId), productId);
        int quantity = cart.getQuantity() > 0 ? cart.getQuantity() - 1 : 0;
        if(quantity <= 0) {
            cartRepo.delete(cart);
        } else {
            cart.setQuantity(quantity);
            cartRepo.save(cart);
        }

    }

    



}
