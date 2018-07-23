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
    private Course course;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CartDetails(){
    }

    public CartDetails(Integer quantity, Cart cart, Course course){
        this.quantity = quantity;
        this.cart = cart;
        this.course = course;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
