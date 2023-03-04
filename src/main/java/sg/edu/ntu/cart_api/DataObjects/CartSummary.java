package sg.edu.ntu.cart_api.DataObjects;

import sg.edu.ntu.cart_api.entity.Cart;

import java.util.List;

public class CartSummary {
    private int quantity;
    private float totalCost;
    private List<Cart> carts;

    public CartSummary() {}

    public CartSummary(int quantity, float totalCost, List<Cart> carts) {
        this.quantity = quantity;
        this.totalCost = totalCost;
        this.carts = carts;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
