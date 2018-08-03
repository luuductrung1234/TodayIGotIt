package ldt.springframework.tigibusiness.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ldt.springframework.tigibusiness.domain.AbstractDomainEntity;
import ldt.springframework.tigibusiness.domain.User;
import ldt.springframework.tigibusiness.enums.RoleType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/*
 * author: Luu Duc Trung
 * https://github.com/luuductrung1234
 * ---
 * 7/25/18
 */


@Entity
public class Role extends AbstractDomainEntity {

    // =======================================
    // =          Attribute Section          =
    // =======================================

    private RoleType type;

    @ManyToMany
    @JoinTable
    @LazyCollection(LazyCollectionOption.FALSE)
    // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "role_id"),
    //     inverseJoinColumns = @joinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();



    // =======================================
    // =        Constructors Section         =
    // =======================================

    public Role(){
    }

    public Role(RoleType type){
        this.type = type;
    }


    // =======================================
    // =         Getters & Setters           =
    // =======================================

    @JsonIgnore
    @JsonProperty(value = "role_user")
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        if(!this.users.contains(user)){
            this.users.add(user);
        }

        if(!user.getRoles().contains(this)){
            user.getRoles().add(this);
        }
    }

    public void removeUser(User user){
        this.users.remove(user);
        user.getRoles().remove(this);
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }
}
