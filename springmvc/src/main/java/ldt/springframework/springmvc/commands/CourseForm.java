package ldt.springframework.springmvc.commands;

import java.math.BigDecimal;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/26/18
 */



public class CourseForm {
    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer courseId;

    private Integer courseVersion;

    private String description;

    private BigDecimal price;

    private String imageUrl;



    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public Integer getCourseId(){
        return this.courseId;
    }

    public void setCourseId(Integer courseId){
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCourseVersion() {
        return courseVersion;
    }

    public void setCourseVersion(Integer courseVersion) {
        this.courseVersion = courseVersion;
    }
}
