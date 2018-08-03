package ldt.springframework.tigibusiness.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/16/18
 */


@Entity
public class Customer extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    @Length(min = 2, max = 10)
    private String firstName;

    @Length(min = 2, max = 10)
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String phoneNumber;

    @Embedded
    private Address billingAddress;

    @Embedded
    private Address shippingAddress;

    @OneToOne
    @MapsId
    private User user;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public Customer(){

    }

    public Customer(Integer id, String firstName,
                    String lastName, String email,
                    String phoneNumber, Address billingAddress,
                    Address shippingAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.shippingAddress = shippingAddress;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @JsonProperty(value = "customer_user")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
