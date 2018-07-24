package ldt.springframework.springmvc.domain;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/18/18
 */


@Entity
public class User extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String username;

    @Transient
    private String password;

    private String encryptedPassowrd;
    private Boolean visible = true;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Customer customer;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Cart cart;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Order> orders = new ArrayList<>();


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEncryptedPassowrd() {
        return encryptedPassowrd;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEncryptedPassowrd(String encryptedPassowrd) {
        this.encryptedPassowrd = encryptedPassowrd;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.setUser(this);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
        cart.setUser(this);
    }

    public Cart removeCart(Cart cart){
        this.cart = null;
        cart.setUser(null);

        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrders(Order order){
        this.orders.add(order);
        order.setUser(this);
    }

    public void removeOrders(Order order){
        this.orders.remove(order);
        order.setUser(null);
    }
}
