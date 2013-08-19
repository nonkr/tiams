package tiams.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Userrole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "userrole", catalog = "tiams")
public class Userrole implements java.io.Serializable {

    // Fields

    private String id;
    private Role role;
    private User user;

    // Constructors

    /** default constructor */
    public Userrole() {
    }

    /** minimal constructor */
    public Userrole(String id) {
        this.id = id;
    }

    /** full constructor */
    public Userrole(String id, Role role, User user) {
        this.id = id;
        this.role = role;
        this.user = user;
    }

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 40)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}