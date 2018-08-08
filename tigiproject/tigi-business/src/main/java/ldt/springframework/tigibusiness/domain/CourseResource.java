package ldt.springframework.tigibusiness.domain;

import ldt.springframework.tigibusiness.enums.ResourceType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/8/18
 */


@Entity
public class CourseResource extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private Integer orderIndex;

    private String title;

    private ResourceType resourceType;

    private String resourcePath;

    @ManyToOne
    private CourseDetails courseDetails;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public CourseResource(){
    }

    public CourseResource(Integer orderIndex, String title, ResourceType resourceType,
                          String resourcePath){
        this.orderIndex = orderIndex;
        this.title = title;
        this.resourceType = resourceType;
        this.resourcePath = resourcePath;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
}
