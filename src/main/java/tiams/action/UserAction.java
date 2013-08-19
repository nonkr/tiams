package tiams.action;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import tiams.dto.Json;
import tiams.dto.SessionInfo;
import tiams.dto.UserDto;
import tiams.service.UserServiceI;
import tiams.util.MyException;
import tiams.util.ResourceUtil;

@ParentPackage("defaultPackage")
@Namespace("/sys")
@Action(value = "userAction", results = {
        @Result(name = "user", location = "/modules/sys/sys_user.jsp"),
        @Result(name = "login", location = "/index.jsp"),
        @Result(name = "showUserInfo", location = "/user/userInfo.jsp") })
public class UserAction extends BaseAction implements ModelDriven<UserDto> {
    private static final Logger logger = Logger.getLogger(UserAction.class);

    private UserDto userDto = new UserDto();
    private UserServiceI userService;

    @Override
    public UserDto getModel() {
        return userDto;
    }

    public UserServiceI getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserServiceI userService) {
        this.userService = userService;
    }

    /**
     * 跳转到用户管理页面
     * 
     * @return
     */
    public String user() {
        return "user";
    }

    /**
     * 新增用户
     */
    public void add() {
        Json j = new Json();
        try {
            UserDto u = null;
            u = userService.add(userDto);
            
            j.setSuccess(true);
            j.setMsg("操作成功！");
            j.setObj(u);
        } catch (MyException e) {
            j.setMsg(e.getMessage());
        } catch (Exception e) {
            j.setMsg("操作失败！");
        }
        writeJson(j);
    }
    
    /**
     * 修改用户
     */
    public void modify() {
        Json j = new Json();
        try {
            UserDto u = null;
            u = userService.modify(userDto);
            
            j.setSuccess(true);
            j.setMsg("操作成功！");
            j.setObj(u);
        } catch (MyException e) {
            j.setMsg(e.getMessage());
        } catch (Exception e) {
            j.setMsg("操作失败！");
        }
        writeJson(j);
    }

    public void delete() {
        Json j = new Json();
        try {
            userService.delete(userDto);
            j.setSuccess(true);
            j.setMsg("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setMsg(e.getMessage());
        }
        writeJson(j);
    }

    public void datagrid() {
        writeJson(userService.datagrid(userDto));
    }

    /**
     * 系统用户登录
     */
    public void login() {
        Json j = new Json();
        UserDto u = userService.login(userDto);
        if (u != null) {
            SessionInfo sessionInfo = saveSessionInfo(u);
            j.setSuccess(true);
            j.setMsg("用户登录成功！");
            j.setObj(sessionInfo);

            changeUserAuths(u);
        } else {
            j.setMsg("用户名或密码错误!");
        }
        writeJson(j);
    }

    private void changeUserAuths(UserDto u) {
        SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest()
                .getSession().getAttribute(ResourceUtil.getSessionInfoName());
        sessionInfo.setAuths(userService.getAuths(u));
    }

    /**
     * 退出系统
     */
    public void logout() {
        Json j = new Json();
        HttpSession session = ServletActionContext.getRequest().getSession();
        if (session != null) {
            session.invalidate();
        }
        j.setSuccess(true);
        writeJson(j);
    }

    /**
     * 登录成功将用户信息保存到SESSION中
     * 
     * @param u
     *            用户对象
     * @return
     */
    private SessionInfo saveSessionInfo(UserDto u) {
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setLoginName(u.getName());
        sessionInfo.setUserId(u.getId());
        sessionInfo.setLoginName(u.getName());
        // sessionInfo.setLoginPassword(user.getCpwd());
        // sessionInfo.setIp(IpUtil.getIpAddr(ServletActionContext.getRequest()));
        ServletActionContext.getRequest().getSession()
                .setAttribute(ResourceUtil.getSessionInfoName(), sessionInfo);
        return sessionInfo;
    }

}
