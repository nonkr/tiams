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

import tiams.dto.BizAcceptDto;
import tiams.dto.Json;
import tiams.dto.SessionInfo;
import tiams.dto.UserDto;
import tiams.service.BizServiceI;
import tiams.service.UserServiceI;
import tiams.util.MyException;
import tiams.util.ResourceUtil;

@ParentPackage("defaultPackage")
@Namespace("/biz")
@Action(value = "bizAcceptAction", results = {
        @Result(name = "page", location = "/modules/biz/biz_ywsl.jsp") })
public class BizAcceptAction extends BaseAction implements ModelDriven<BizAcceptDto> {
    private static final Logger logger = Logger.getLogger(BizAcceptAction.class);

    private BizAcceptDto bizAcceptDto = new BizAcceptDto();
    private BizServiceI bizService;

    @Override
    public BizAcceptDto getModel() {
        return bizAcceptDto;
    }

    public BizServiceI getBizService() {
        return bizService;
    }

    @Autowired
    public void setBizService(BizServiceI bizService) {
        this.bizService = bizService;
    }


    public String page() {
        return "page";
    }

    public void add() {
        Json j = new Json();
        try {
            BizAcceptDto b = null;
            b = bizService.add(bizAcceptDto);
            
            j.setSuccess(true);
            j.setMsg("操作成功！");
            j.setObj(b);
        } catch (MyException e) {
            j.setMsg(e.getMessage());
        } catch (Exception e) {
            j.setMsg("操作失败！");
        }
        writeJson(j);
    }
}
