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
import tiams.dto.BizQueryDto;
import tiams.dto.Json;
import tiams.dto.SessionInfo;
import tiams.dto.UserDto;
import tiams.service.BizServiceI;
import tiams.service.UserServiceI;
import tiams.util.MyException;
import tiams.util.ResourceUtil;

@ParentPackage("defaultPackage")
@Namespace("/biz")
@Action(value = "bizQueryAction", results = {
        @Result(name = "page", location = "/modules/biz/biz_ywcx.jsp") })
public class BizQueryAction extends BaseAction implements ModelDriven<BizQueryDto> {
    private static final Logger logger = Logger.getLogger(BizQueryAction.class);

    private BizQueryDto bizQueryDto = new BizQueryDto();
    private BizServiceI bizService;

    @Override
    public BizQueryDto getModel() {
        return bizQueryDto;
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
    
    public void datagrid() {
        writeJson(bizService.datagrid(bizQueryDto));
    }

}
