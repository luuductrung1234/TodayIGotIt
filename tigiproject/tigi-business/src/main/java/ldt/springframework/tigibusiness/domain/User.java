package ldt.springframework.tigibusiness.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ldt.springframework.tigibusiness.domain.security.Role;
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
    private Boolean enabled = true;
    private Integer failedLoginAttempts = 0;

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

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CourseOwner> courseOwners = new ArrayList<>();

    @ManyToMany
    @JoinTable
    @LazyCollection(LazyCollectionOption.FALSE)
    // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
    //     inverseJoinColumns = @joinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();




    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public String getUsername() {
        return username;
    }


    @JsonIgnore
    @JsonProperty(value = "user_password")
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @JsonProperty(value = "user_encryptpassword")
    public String getEncryptedPassowrd() {
        return encryptedPassowrd;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        customer.setUser(this);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }


    // =======================================
    // =            Cart Access              =
    // =======================================

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


    // =======================================
    // =            Order Access             =
    // =======================================
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


    // =======================================
    // =            Role Access              =
    // =======================================

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        if(!this.roles.contains(role)){
            this.roles.add(role);
        }

        if(!role.getUsers().contains(this)){
            role.getUsers().add(this);
        }
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }


    // =======================================
    // =           Course Access             =
    // =======================================

    public List<CourseOwner> getCourseOwners() {
        return courseOwners;
    }

    public void setCourseOwners(List<CourseOwner> courseOwners) {
        this.courseOwners = courseOwners;
    }

    public void addCourseOwer(CourseOwner courseOwner){
        courseOwner.setUser(this);
        this.courseOwners.add(courseOwner);
    }

    public void removeCourse(CourseOwner courseOwner){
        courseOwner.setUser(null);
        this.courseOwners.remove(courseOwner);
    }
}
