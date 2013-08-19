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

/**
 * Biz entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz", catalog = "tiams")
public class Biz implements java.io.Serializable {

    // Fields

    private String id;
    private String bizYwbh;
    private Integer bizYwlx;
    private Date bizSlsj;
    private String bizWtdw;
    private String bizLxdz;
    private String bizLxr;
    private String bizSjhm;
    private String bizLxdh;
    private String bizEmail;
    private Set<BizXm> bizXms = new HashSet<BizXm>(0);

    // Constructors

    /** default constructor */
    public Biz() {
    }

    /** minimal constructor */
    public Biz(String id) {
        this.id = id;
    }

    /** full constructor */
    public Biz(String id, String bizYwbh, Integer bizYwlx, Date bizSlsj, String bizWtdw,
            String bizLxdz, String bizLxr, String bizSjhm, String bizLxdh,
            String bizEmail, Set<BizXm> bizXms) {
        this.id = id;
        this.bizYwbh = bizYwbh;
        this.bizYwlx = bizYwlx;
        this.bizSlsj = bizSlsj;
        this.bizWtdw = bizWtdw;
        this.bizLxdz = bizLxdz;
        this.bizLxr = bizLxr;
        this.bizSjhm = bizSjhm;
        this.bizLxdh = bizLxdh;
        this.bizEmail = bizEmail;
        this.bizXms = bizXms;
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

    @Column(name = "biz_ywbh", length = 20)
    public String getBizYwbh() {
        return this.bizYwbh;
    }

    public void setBizYwbh(String bizYwbh) {
        this.bizYwbh = bizYwbh;
    }

    @Column(name = "biz_ywlx")
    public Integer getBizYwlx() {
        return this.bizYwlx;
    }

    public void setBizYwlx(Integer bizYwlx) {
        this.bizYwlx = bizYwlx;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "biz_slsj", length = 10)
    public Date getBizSlsj() {
        return this.bizSlsj;
    }

    public void setBizSlsj(Date bizSlsj) {
        this.bizSlsj = bizSlsj;
    }

    @Column(name = "biz_wtdw", length = 50)
    public String getBizWtdw() {
        return this.bizWtdw;
    }

    public void setBizWtdw(String bizWtdw) {
        this.bizWtdw = bizWtdw;
    }

    @Column(name = "biz_lxdz", length = 200)
    public String getBizLxdz() {
        return this.bizLxdz;
    }

    public void setBizLxdz(String bizLxdz) {
        this.bizLxdz = bizLxdz;
    }

    @Column(name = "biz_lxr", length = 20)
    public String getBizLxr() {
        return this.bizLxr;
    }

    public void setBizLxr(String bizLxr) {
        this.bizLxr = bizLxr;
    }

    @Column(name = "biz_sjhm", length = 20)
    public String getBizSjhm() {
        return this.bizSjhm;
    }

    public void setBizSjhm(String bizSjhm) {
        this.bizSjhm = bizSjhm;
    }

    @Column(name = "biz_lxdh", length = 20)
    public String getBizLxdh() {
        return this.bizLxdh;
    }

    public void setBizLxdh(String bizLxdh) {
        this.bizLxdh = bizLxdh;
    }

    @Column(name = "biz_email", length = 50)
    public String getBizEmail() {
        return this.bizEmail;
    }

    public void setBizEmail(String bizEmail) {
        this.bizEmail = bizEmail;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "biz")
    public Set<BizXm> getBizXms() {
        return this.bizXms;
    }

    public void setBizXms(Set<BizXm> bizXms) {
        this.bizXms = bizXms;
    }

}