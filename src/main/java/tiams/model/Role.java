package tiams.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role", catalog = "tiams")
public class Role implements java.io.Serializable {

    // Fields

    private String id;
    private String name;
    private String remark;
    private Set<Userrole> userroles = new HashSet<Userrole>(0);
    private Set<Roleauth> roleauths = new HashSet<Roleauth>(0);

    // Constructors

    /** default constructor */
    public Role() {
    }

    /** minimal constructor */
    public Role(String id) {
        this.id = id;
    }

    /** full constructor */
    public Role(String id, String name, String remark, Set<Userrole> userroles,
            Set<Roleauth> roleauths) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.userroles = userroles;
        this.roleauths = roleauths;
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

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<Userrole> getUserroles() {
        return this.userroles;
    }

    public void setUserroles(Set<Userrole> userroles) {
        this.userroles = userroles;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    public Set<Roleauth> getRoleauths() {
        return this.roleauths;
    }

    public void setRoleauths(Set<Roleauth> roleauths) {
        this.roleauths = roleauths;
    }

}