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
 * Menu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "menu", catalog = "tiams")
public class Menu implements java.io.Serializable {

    // Fields

    private String id;
    private Menu menu;
    private String text;
    private String iconCls;
    private String url;
    private Integer seq;
    private Set<Menu> menus = new HashSet<Menu>(0);

    // Constructors

    /** default constructor */
    public Menu() {
    }

    /** minimal constructor */
    public Menu(String id) {
        this.id = id;
    }

    /** full constructor */
    public Menu(String id, Menu menu, String text, String iconCls, String url, Integer seq, Set<Menu> menus) {
        this.id = id;
        this.menu = menu;
        this.text = text;
        this.iconCls = iconCls;
        this.url = url;
        this.seq = seq;
        this.menus = menus;
    }

    // Property accessors
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Column(name = "text", length = 100)
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "iconCls", length = 50)
    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    @Column(name = "url", length = 100)
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "seq")
    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "menu")
    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

}