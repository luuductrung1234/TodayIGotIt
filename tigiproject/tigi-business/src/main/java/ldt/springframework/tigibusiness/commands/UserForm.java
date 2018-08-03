package ldt.springframework.tigibusiness.commands;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ldt.springframework.tigibusiness.domain.Cart;
import ldt.springframework.tigibusiness.domain.security.Role;
import ldt.springframework.tigibusiness.enums.RoleType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 *
 * ---
 * This class is a flat model that only for validate data input from the user
 */


public class UserForm {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer userId;
    private Integer userVersion;
    @NotEmpty
    @Size(min = 10, max = 200)
    private String userName;
    @NotEmpty
    private String passwordText;
    @NotEmpty
    private String passwordTextConf;
    private String passwordEncrypted;
    private List<Role> userRoles;
    private Cart userCart;

    private Integer customerId;
    private Integer customerVersion;
    @NotEmpty
    @Length(min = 2, max = 10)
    private String firstName;
    @NotEmpty
    @Length(min = 2, max = 10)
    private String lastName;
    @Email
    private String email;
    @Pattern(regexp="(^$|[0-9]{10,12})")
    private String phoneNumber;

    @NotEmpty
    private String billingAddressLine1;
    private String billingAddressLine2;
    @NotEmpty
    private String billingCity;
    @NotEmpty
    private String billingState;
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{3,7})")
    private String billingZipCode;

    @NotEmpty
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    @NotEmpty
    private String shippingCity;
    @NotEmpty
    private String shippingState;
    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{3,7})")
    private String shippingZipCode;


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserVersion() {
        return userVersion;
    }

    public void setUserVersion(Integer userVersion) {
        this.userVersion = userVersion;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCustomerVersion() {
        return customerVersion;
    }

    public void setCustomerVersion(Integer customerVersion) {
        this.customerVersion = customerVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Role> getUserRoles(){
        return this.userRoles;
    }

    public void setUserRoles(List<Role> userRoles){
        this.userRoles = userRoles;
    }

    @JsonIgnore
    @JsonProperty(value = "userform_password")
    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public String getPasswordTextConf() {
        return passwordTextConf;
    }

    public void setPasswordTextConf(String passwordTextConf) {
        this.passwordTextConf = passwordTextConf;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonIgnore
    @JsonProperty(value = "userform_encryptpassword")
    public String getPasswordEncrypted() {
        return passwordEncrypted;
    }

    public void setPasswordEncrypted(String passwordEncrypted) {
        this.passwordEncrypted = passwordEncrypted;
    }

    public Cart getUserCart() {
        return userCart;
    }

    public void setUserCart(Cart userCart) {
        this.userCart = userCart;
    }

    public String getBillingAddressLine1() {
        return billingAddressLine1;
    }

    public void setBillingAddressLine1(String billingAddressLine1) {
        this.billingAddressLine1 = billingAddressLine1;
    }

    public String getBillingAddressLine2() {
        return billingAddressLine2;
    }

    public void setBillingAddressLine2(String billingAddressLine2) {
        this.billingAddressLine2 = billingAddressLine2;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZipCode() {
        return billingZipCode;
    }

    public void setBillingZipCode(String billingZipcode) {
        this.billingZipCode = billingZipcode;
    }

    public String getShippingAddressLine1() {
        return shippingAddressLine1;
    }

    public void setShippingAddressLine1(String shippingAddressLine1) {
        this.shippingAddressLine1 = shippingAddressLine1;
    }

    public String getShippingAddressLine2() {
        return shippingAddressLine2;
    }

    public void setShippingAddressLine2(String shippingAddressLine2) {
        this.shippingAddressLine2 = shippingAddressLine2;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingZipCode() {
        return shippingZipCode;
    }

    public void setShippingZipCode(String shippingZipcode) {
        this.shippingZipCode = shippingZipcode;
    }
}
