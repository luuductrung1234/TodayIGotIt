package ldt.springframework.tigibusiness.commands;

import ldt.springframework.tigibusiness.domain.CourseTag;
import ldt.springframework.tigibusiness.domain.TagTracking;
import ldt.springframework.tigibusiness.enums.TagName;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

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

    @NotEmpty
    private String description;

    private Integer buyCount;

    private Integer viewCount;

    private List<CourseTag> courseTags;

    @NotEmpty
    @Min(1)
    @Pattern(regexp="(^$|[0-9]{1,6})")
    private String price;

    @NotEmpty
    @URL
    private String imageUrl;

    @NotEmpty
    private String mediaPath;



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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public List<CourseTag> getCourseTags() {
        return courseTags;
    }

    public void setCourseTags(List<CourseTag> courseTags) {
        this.courseTags = courseTags;
    }
}
