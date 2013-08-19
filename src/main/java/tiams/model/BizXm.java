package tiams.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * BizXm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "biz_xm", catalog = "tiams")
public class BizXm implements java.io.Serializable {

    // Fields

    private String id;
    private Biz biz;
    private String xmLx;
    private String xmYj;
    private Integer xmFp;

    // Constructors

    /** default constructor */
    public BizXm() {
    }

    /** minimal constructor */
    public BizXm(String id) {
        this.id = id;
    }

    /** full constructor */
    public BizXm(String id, Biz biz, String xmLx, String xmYj, Integer xmFp) {
        this.id = id;
        this.biz = biz;
        this.xmLx = xmLx;
        this.xmYj = xmYj;
        this.xmFp = xmFp;
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
    @JoinColumn(name = "xm_ywid")
    public Biz getBiz() {
        return this.biz;
    }

    public void setBiz(Biz biz) {
        this.biz = biz;
    }

    @Column(name = "xm_lx", length = 10)
    public String getXmLx() {
        return this.xmLx;
    }

    public void setXmLx(String xmLx) {
        this.xmLx = xmLx;
    }

    @Column(name = "xm_yj", length = 10)
    public String getXmYj() {
        return this.xmYj;
    }

    public void setXmYj(String xmYj) {
        this.xmYj = xmYj;
    }

    @Column(name = "xm_fp")
    public Integer getXmFp() {
        return this.xmFp;
    }

    public void setXmFp(Integer xmFp) {
        this.xmFp = xmFp;
    }

}