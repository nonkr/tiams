package tiams.interceptor;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import tiams.dto.AuthDto;
import tiams.dto.SessionInfo;
import tiams.util.RequestUtil;
import tiams.util.ResourceUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器
 * 
 * @author nonkr
 * 
 */
public class AuthInterceptor extends MethodFilterInterceptor {

	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
	    logger.info("权限拦截器");
		ActionContext actionContext = actionInvocation.getInvocationContext();
//		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
//		if (sessionInfo.getLoginName().equals("admin")) {// admin用户不需要验证权限
//			return actionInvocation.invoke();
//		}
		String requestPath = RequestUtil.getRequestPath(ServletActionContext.getRequest());
//		logger.info(actionInvocation.getAction().getClass());
//		logger.info(requestPath);
//		List<Auth> auths = sessionInfo.getAuths();
//		if (auths != null && auths.size() > 0) {
//			boolean b = false;
//			for (Auth a : auths) {
//				if (requestPath.equals(a.getCurl())) {
//					b = true;
//					break;
//				}
//			}
//			if (b) {
//				return actionInvocation.invoke();
//			}
//		}
//		ServletActionContext.getRequest().setAttribute("msg", "您没有访问此功能的权限！权限路径为[" + requestPath + "]请联系管理员给你赋予相应权限。");
//		return "noAuth";
		return actionInvocation.invoke();
	}

}
