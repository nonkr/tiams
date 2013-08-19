package tiams.service;

import java.util.List;

import tiams.dto.MenuDto;

public interface MenuServiceI {

	public List<MenuDto> getTreeNode(String id);

	public List<MenuDto> getAllTreeNode();

}
