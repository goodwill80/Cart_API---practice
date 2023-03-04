package sg.edu.ntu.cart_api.DataObjects;

import sg.edu.ntu.cart_api.entity.Product;


public class CartItem {

    private Integer userid;
    private Integer productId;
    private int quantity;


    public CartItem(){};

    public CartItem(Integer userid, Integer productId, int quantity) {
        this.userid = userid;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
