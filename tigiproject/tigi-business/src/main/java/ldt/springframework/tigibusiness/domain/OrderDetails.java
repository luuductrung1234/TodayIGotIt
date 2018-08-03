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
 * 7/22/18
 */

@Entity
public class OrderDetails extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer quantity;

    @ManyToOne
    private Order order;

    @OneToOne
    private Course course;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public OrderDetails(){
    }

    public OrderDetails(Integer quantity, Order order, Course course){
        this.quantity = quantity;
        this.order = order;
        this.course = course;
    }

    public OrderDetails(Integer quantity, Course course){
        this.quantity = quantity;
        this.course = course;
    }



    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @JsonIgnore
    @JsonProperty(value = "orderdetails_order")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
