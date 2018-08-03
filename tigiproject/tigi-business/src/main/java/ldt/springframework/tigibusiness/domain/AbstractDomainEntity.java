package ldt.springframework.tigibusiness.domain;

import javax.persistence.*;
import java.util.Date;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/22/18
 */

@MappedSuperclass
public class AbstractDomainEntity implements DomainObject{
    // =======================================
    // =          Attribute Section          =
    // =======================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Version
    private Integer version;

    private Date dateCreated;
    private Date lastUpdated;


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated==null) {
            dateCreated = new Date();
        }
    }
}
