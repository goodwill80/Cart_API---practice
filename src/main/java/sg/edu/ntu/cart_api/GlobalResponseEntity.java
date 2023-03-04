package sg.edu.ntu.cart_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sg.edu.ntu.cart_api.Exceptions.CartNotFoundException;
import sg.edu.ntu.cart_api.Exceptions.ErrorPojo;
import sg.edu.ntu.cart_api.Exceptions.ProductNotFoundException;
import sg.edu.ntu.cart_api.Exceptions.UserNotFoundException;

import java.util.Arrays;

@ControllerAdvice
public class GlobalResponseEntity extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CartNotFoundException.class, ProductNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(RuntimeException ex) {
        ErrorPojo error = new ErrorPojo(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
