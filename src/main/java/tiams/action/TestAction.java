package tiams.action;

import org.apache.log4j.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("defaultPackage")
@Namespace("/test")
@Action(value = "testAction", results = { @Result(name = "success", location = "/test/test.jsp") })
public class TestAction extends ActionSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestAction.class);

	@Override
	public String execute() throws Exception {
		logger.info("TestAction execute()");
		return super.execute();
	}

}
