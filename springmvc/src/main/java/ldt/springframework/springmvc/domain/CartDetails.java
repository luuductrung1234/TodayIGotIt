package ldt.springframework.springmvc.domain;

import javax.persistence.*;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/19/18
 */

@Entity
public class CartDetails extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer quantity;

    @ManyToOne
    private Cart cart;

    @OneToOne
    private Product product;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CartDetails(){
    }

    public CartDetails(Integer quantity, Cart cart, Product product){
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
