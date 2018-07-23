package ldt.springframework.springmvc.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/15/18
 */


@Entity
public class Course extends AbstractDomainEntity {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    @Length(min = 3, max = 100)
    private String description;

    @Positive
    private BigDecimal price;

    private String imageUrl;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public Course() {
    }

    public Course(Integer id, String description, BigDecimal price, String imageUrl) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
