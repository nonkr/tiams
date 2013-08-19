package tiams.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import tiams.dto.MenuDto;
import tiams.service.MenuServiceI;

import com.opensymphony.xwork2.ModelDriven;

@ParentPackage("defaultPackage")
@Action(value = "menuAction")
public class MenuAction extends BaseAction implements ModelDriven<MenuDto> {

	private static final Logger logger = Logger.getLogger(MenuAction.class);

	private MenuDto menu = new MenuDto();

	private MenuServiceI menuService;

	public MenuServiceI getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}

	@Override
	public MenuDto getModel() {
		return menu;
	}

	/**
	 * 异步获取树节点
	 */
	public void getTreeNode() {
		super.writeJson(menuService.getTreeNode(menu.getId()));
	}

	/**
	 * 一次获取整棵树
	 */
	public void getAllTreeNode() {
		super.writeJson(menuService.getAllTreeNode());
	}

}
