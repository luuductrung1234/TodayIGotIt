package ldt.springframework.tigibusiness.domain;

import ldt.springframework.tigibusiness.enums.TagName;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 8/10/18
 */


@Entity
public class TagTracking extends AbstractDomainEntity{

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private String tagName;

    private Integer searchCount;


    // =======================================
    // =        Constructors Section         =
    // =======================================

    public TagTracking(){

    }

    public TagTracking(String tagName, Integer searchCount){
        this.tagName = tagName;
        this.searchCount = searchCount;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }

}
