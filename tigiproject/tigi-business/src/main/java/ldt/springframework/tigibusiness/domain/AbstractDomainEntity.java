package ldt.springframework.tigibusiness.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
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

    private LocalDate dateCreated;

    private LocalDate lastUpdated;


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

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setDateCreated(LocalDate dateCreated){
        this.dateCreated = dateCreated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = LocalDate.now();
        if (dateCreated==null) {
            dateCreated = LocalDate.now();
        }
    }
}
