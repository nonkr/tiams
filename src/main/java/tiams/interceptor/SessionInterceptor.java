package tiams.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import tiams.dto.SessionInfo;
import tiams.util.ResourceUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * session拦截器
 * 
 * @author nonkr
 * 
 */
public class SessionInterceptor extends MethodFilterInterceptor {

	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);

	@Override
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ResourceUtil.getSessionInfoName());
		logger.info("SessionInterceptor");
		if (sessionInfo == null) {
//			ServletActionContext.getRequest().setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
		    logger.info("noSession");
			return "noSession";
		}
		return actionInvocation.invoke();
	}

}
