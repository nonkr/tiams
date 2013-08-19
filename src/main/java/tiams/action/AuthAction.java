package tiams.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import tiams.dto.AuthDto;
import tiams.dto.Json;
import tiams.service.AuthServiceI;
import tiams.util.ExceptionUtil;
import tiams.util.MyException;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 权限ACTION
 * 
 * @author nonkr
 * 
 */
@ParentPackage("defaultPackage")
@Namespace("/sys")
@Action(value = "authAction", results = {
        @Result(name = "auth", location = "/modules/sys/sys_auth.jsp"),
        @Result(name = "login", location = "/index.jsp") })
public class AuthAction extends BaseAction implements ModelDriven<AuthDto> {

    private static final Logger logger = Logger.getLogger(AuthAction.class);

    private AuthDto authDto = new AuthDto();

    private AuthServiceI authService;

    public AuthServiceI getAuthService() {
        return authService;
    }

    @Autowired
    public void setAuthService(AuthServiceI authService) {
        this.authService = authService;
    }

    @Override
    public AuthDto getModel() {
        return authDto;
    }

    public String auth() {
        return "auth";
    }

    /**
     * 获得权限treegrid
     */
    public void treegrid() {
        writeJson(authService.treegrid());
    }
    
    /**
     * 获得权限treegridById
     */
    public void treegridById() {
        writeJson(authService.treegridById(authDto));
    }

    /**
     * 删除一个权限
     */
    public void delete() {
        Json j = new Json();
        try {
            authService.delete(authDto);
            j.setSuccess(true);
            j.setMsg("删除成功！");
            j.setObj(authDto.getId());
        } catch (Exception e) {
            logger.error(ExceptionUtil.getExceptionMessage(e));
            j.setMsg("删除失败！");
        }
        writeJson(j);
    }

    /**
     * 获取权限树
     */
    public void tree() {
        writeJson(authService.tree(authDto, false));
    }

    /**
     * 获取权限树,递归子节点
     */
    public void treeRecursive() {
        writeJson(authService.tree(authDto, true));
    }

    /**
     * 权限树,所有人都有权限访问这个
     */
    public void authTreeRecursive() {
        writeJson(authService.tree(authDto, true));
    }

    /**
     * 编辑权限
     */
    public void modify() {
        Json j = new Json();
        try {
            authService.modify(authDto);
            j.setSuccess(true);
            j.setMsg("编辑成功!");
            j.setObj(authDto.getPid());
        } catch (Exception e) {
            j.setMsg("编辑失败！");
        }
        writeJson(j);
    }

    /**
     * 添加权限
     */
    public void add() {
        Json j = new Json();
        try {
            authService.add(authDto);
            j.setSuccess(true);
            j.setMsg("添加成功!");
            j.setObj(authDto.getPid());
        } catch (Exception e) {
            j.setMsg("添加失败！");
        }
        writeJson(j);
    }

}
