package ldt.springframework.springmvc.domain;

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
    private Product product;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public OrderDetails(){
    }

    public OrderDetails(Integer quantity, Order order, Product product){
        this.quantity = quantity;
        this.order = order;
        this.product = product;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
