package tiams.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Roleauth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "roleauth", catalog = "tiams")
public class Roleauth implements java.io.Serializable {

    // Fields

    private String id;
    private Auth auth;
    private Role role;

    // Constructors

    /** default constructor */
    public Roleauth() {
    }

    /** minimal constructor */
    public Roleauth(String id) {
        this.id = id;
    }

    /** full constructor */
    public Roleauth(String id, Auth auth, Role role) {
        this.id = id;
        this.auth = auth;
        this.role = role;
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
    @JoinColumn(name = "authId")
    public Auth getAuth() {
        return this.auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}