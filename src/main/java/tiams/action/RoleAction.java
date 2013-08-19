package tiams.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import tiams.dto.Json;
import tiams.dto.RoleDto;
import tiams.service.RoleServiceI;
import tiams.util.MyException;

@ParentPackage("defaultPackage")
@Namespace("/sys")
@Action(value = "roleAction", results = {
        @Result(name = "role", location = "/modules/sys/sys_role.jsp"),
        @Result(name = "login", location = "/index.jsp") })
public class RoleAction extends BaseAction implements ModelDriven<RoleDto> {
    private static final Logger logger = Logger.getLogger(RoleAction.class);

    private RoleDto roleDto = new RoleDto();
    private RoleServiceI roleService;

    @Override
    public RoleDto getModel() {
        return roleDto;
    }

    public RoleServiceI getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleServiceI roleService) {
        this.roleService = roleService;
    }

    /**
     * 跳转到角色管理页面
     * 
     * @return
     */
    public String role() {
        return "role";
    }

    /**
     * 显示角色
     */
    public void datagrid() {
        writeJson(roleService.datagrid(roleDto));
    }

    /**
     * 新增角色
     */
    public void add() {
        Json j = new Json();
        try {
            RoleDto r = null;
            r = roleService.add(roleDto);

            j.setSuccess(true);
            j.setMsg("操作成功！");
            j.setObj(r);
        } catch (MyException e) {
            j.setMsg(e.getMessage());
        } catch (Exception e) {
            j.setMsg("操作失败！");
        }
        writeJson(j);
    }

    /**
     * 修改角色
     */
    public void modify() {
        Json j = new Json();
        try {
            RoleDto r = null;
            r = roleService.modify(roleDto);

            j.setSuccess(true);
            j.setMsg("操作成功！");
            j.setObj(r);
        } catch (MyException e) {
            j.setMsg(e.getMessage());
        } catch (Exception e) {
            j.setMsg("操作失败！");
        }
        writeJson(j);
    }

    /**
     * 删除角色
     */
    public void delete() {
        Json j = new Json();
        try {
            roleService.delete(roleDto);
            j.setSuccess(true);
            j.setMsg("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        writeJson(j);
    }

}
