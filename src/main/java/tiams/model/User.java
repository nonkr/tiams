package tiams.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "tiams", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class User implements java.io.Serializable {

    // Fields

    private String id;
    private String name;
    private String pwd;
    private Date createdatetime;
    private Date modifydatetime;
    private Set<Userrole> userroles = new HashSet<Userrole>(0);

    // Constructors

    /** default constructor */
    public User() {
    }

    /** minimal constructor */
    public User(String id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    /** full constructor */
    public User(String id, String name, String pwd, Date createdatetime,
            Date modifydatetime, Set<Userrole> userroles) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.createdatetime = createdatetime;
        this.modifydatetime = modifydatetime;
        this.userroles = userroles;
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

    @Column(name = "name", unique = true, nullable = false, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "pwd", nullable = false, length = 40)
    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdatetime", length = 10)
    public Date getCreatedatetime() {
        return this.createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifydatetime", length = 10)
    public Date getModifydatetime() {
        return this.modifydatetime;
    }

    public void setModifydatetime(Date modifydatetime) {
        this.modifydatetime = modifydatetime;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Userrole> getUserroles() {
        return this.userroles;
    }

    public void setUserroles(Set<Userrole> userroles) {
        this.userroles = userroles;
    }

}