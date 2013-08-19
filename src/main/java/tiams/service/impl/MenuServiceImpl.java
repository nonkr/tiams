package tiams.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiams.dao.BaseDaoI;
import tiams.model.Menu;
import tiams.dto.MenuDto;
import tiams.service.MenuServiceI;

@Service("menuService")
public class MenuServiceImpl implements MenuServiceI {

	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class);

	private BaseDaoI<Menu> menuDao;


    public BaseDaoI<Menu> getMenuDao() {
        return menuDao;
    }

    @Autowired
    public void setMenuDao(BaseDaoI<Menu> menuDao) {
        this.menuDao = menuDao;
    }

    @Override
	public List<MenuDto> getTreeNode(String id) {
		List<MenuDto> nl = new ArrayList<MenuDto>();
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")) {
			// 查询所有根节点
			hql = "from Menu m where m.menu is null";
		} else {
			// 异步加载当前id下的子节点
			hql = "from Menu m where m.menu.id = :id ";
			params.put("id", id);
		}
		List<Menu> l = menuDao.find(hql, params);
		if (l != null && l.size() > 0) {
			for (Menu t : l) {
				MenuDto m = new MenuDto();
				BeanUtils.copyProperties(t, m);
				Set<Menu> set = t.getMenus();
				if (set != null && !set.isEmpty()) {
					m.setState("closed");// 节点以文件夹的形式体现
				} else {
					m.setState("open");// 节点以文件的形式体现
				}
				nl.add(m);
			}
		}
		return nl;
	}

	@Override
	public List<MenuDto> getAllTreeNode() {
		List<MenuDto> nl = new ArrayList<MenuDto>();
		String hql = "from Menu m order by m.seq";
		List<Menu> l = menuDao.find(hql);
		if (l != null && l.size() > 0) {
			for (Menu t : l) {
				MenuDto menuDto = new MenuDto();
				BeanUtils.copyProperties(t, menuDto);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", t.getUrl());
				menuDto.setAttributes(attributes);
				Menu m = t.getMenu();// 获得当前节点的父节点对象
				if (m != null) {
					menuDto.setPid(m.getId());
				}
				nl.add(menuDto);
			}
		}
		return nl;
	}

}
