package tiams.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Auth entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "auth", catalog = "tiams")
public class Auth implements java.io.Serializable {

    // Fields

    private String id;
    private Auth auth;
    private String name;
    private String url;
    private String remark;
    private Integer seq;
    private Set<Roleauth> roleauths = new HashSet<Roleauth>(0);
    private Set<Auth> auths = new HashSet<Auth>(0);

    // Constructors

    /** default constructor */
    public Auth() {
    }

    /** minimal constructor */
    public Auth(String id) {
        this.id = id;
    }

    /** full constructor */
    public Auth(String id, Auth auth, String name, String url, String remark,
            Integer seq, Set<Roleauth> roleauths, Set<Auth> auths) {
        this.id = id;
        this.auth = auth;
        this.name = name;
        this.url = url;
        this.remark = remark;
        this.seq = seq;
        this.roleauths = roleauths;
        this.auths = auths;
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
    @JoinColumn(name = "pid")
    public Auth getAuth() {
        return this.auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "url", length = 200)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "remark", length = 200)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "seq")
    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "auth")
    public Set<Roleauth> getRoleauths() {
        return this.roleauths;
    }

    public void setRoleauths(Set<Roleauth> roleauths) {
        this.roleauths = roleauths;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "auth")
    public Set<Auth> getAuths() {
        return this.auths;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

}