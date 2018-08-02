package ldt.springframework.springmvc.domain;

import ldt.springframework.springmvc.enums.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/22/18
 */

@Entity
@Table(name = "order_table")
public class Order  extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    @ManyToOne
    private User user;

    @Embedded
    private Address shipToAddress;

    private OrderStatus orderStatus;

    private Date dateShipped;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "order",
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderDetails> orderDetails = new ArrayList<>();


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public Order(){
    }

    public Order(User user, Address shipToAddress, Date dateShipped, OrderStatus orderStatus){
        this.user = user;
        this.shipToAddress = shipToAddress;
        this.dateShipped = dateShipped;
        this.orderStatus = orderStatus;
    }

    public Order(Address shipToAddress, Date dateShipped, OrderStatus orderStatus){
        this.shipToAddress = shipToAddress;
        this.dateShipped = dateShipped;
        this.orderStatus = orderStatus;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Address getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(Address shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetails(OrderDetails orderDetail){
        orderDetail.setOrder(this);
        orderDetails.add(orderDetail);
    }

    public void removeOrderDetail(OrderDetails orderDetail){
        orderDetail.setOrder(null);
        orderDetails.remove(orderDetail);
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
