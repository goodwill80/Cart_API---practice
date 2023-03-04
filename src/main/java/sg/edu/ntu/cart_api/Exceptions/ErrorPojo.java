package sg.edu.ntu.cart_api.Exceptions;

import java.util.List;

public class ErrorPojo {
    private List<String> message;

    public ErrorPojo(List<String> message) {
        this.message = message;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
