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
 * 7/19/18
 */


@Entity
public class Cart extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    @OneToOne
    @MapsId
    private User user;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "cart",
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CartDetails> cartDetails = new ArrayList<>();


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public Cart(){
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartDetails> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetails> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public void addCartDetail(CartDetails cartDetail){
        cartDetails.add(cartDetail);
        cartDetail.setCart(this);
    }

    public void removeCartDetail(CartDetails cartDetail){
        cartDetail.setCart(null);
        this.cartDetails.remove(cartDetail);
    }
}
