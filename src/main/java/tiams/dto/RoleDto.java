package tiams.dto;

public class RoleDto implements java.io.Serializable {

    private String id;
    private String ids;
    private String name;
    private String remark;
    private String authIds;
    private String authNames;

    private int page;// 当前页
    private int rows;// 每页显示记录数
    private String sort;// 排序字段名
    private String order;// 按什么排序(asc,desc)

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getAuthIds() {
        return authIds;
    }

    public void setAuthIds(String authIds) {
        this.authIds = authIds;
    }

    public String getAuthNames() {
        return authNames;
    }

    public void setAuthNames(String authNames) {
        this.authNames = authNames;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}