package ldt.springframework.tigibusiness.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

    public CartDetails(Integer quantity, Course course){
        this.quantity = quantity;
        this.course = course;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @JsonIgnore
    @JsonProperty(value = "cartdetails_cart")
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
