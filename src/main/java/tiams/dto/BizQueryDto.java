package tiams.dto;

import java.util.Date;

public class BizQueryDto {
    private String id;
    private String ids;
    private int page;// 当前页
    private int rows;// 每页显示记录数
    private String sort;// 排序字段名
    private String order;// 按什么排序(asc,desc)
    
    private String bizYwbh;
    public String getBizYwbh() {
        return bizYwbh;
    }

    public void setBizYwbh(String bizYwbh) {
        this.bizYwbh = bizYwbh;
    }

    private Integer bizYwlx;
    private Date bizSlsj;
    private String bizWtdw;
    private String bizLxdz;
    private String bizLxr;
    private String bizSjhm;
    private String bizLxdh;
    private String bizEmail;

    public Integer getBizYwlx() {
        return bizYwlx;
    }

    public void setBizYwlx(Integer bizYwlx) {
        this.bizYwlx = bizYwlx;
    }

    public Date getBizSlsj() {
        return bizSlsj;
    }

    public void setBizSlsj(Date bizSlsj) {
        this.bizSlsj = bizSlsj;
    }

    public String getBizWtdw() {
        return bizWtdw;
    }

    public void setBizWtdw(String bizWtdw) {
        this.bizWtdw = bizWtdw;
    }

    public String getBizLxdz() {
        return bizLxdz;
    }

    public void setBizLxdz(String bizLxdz) {
        this.bizLxdz = bizLxdz;
    }

    public String getBizLxr() {
        return bizLxr;
    }

    public void setBizLxr(String bizLxr) {
        this.bizLxr = bizLxr;
    }

    public String getBizSjhm() {
        return bizSjhm;
    }

    public void setBizSjhm(String bizSjhm) {
        this.bizSjhm = bizSjhm;
    }

    public String getBizLxdh() {
        return bizLxdh;
    }

    public void setBizLxdh(String bizLxdh) {
        this.bizLxdh = bizLxdh;
    }

    public String getBizEmail() {
        return bizEmail;
    }

    public void setBizEmail(String bizEmail) {
        this.bizEmail = bizEmail;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
