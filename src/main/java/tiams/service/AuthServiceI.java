package tiams.service;

import java.util.List;

import tiams.dto.AuthDto;
import tiams.dto.TreeNode;

public interface AuthServiceI {

	/**
	 * 获得权限treegrid
	 * 
	 * @return
	 */
	public List<AuthDto> treegrid();
	
	/**
	 * 获得权限treegridById
	 * 
	 * @param authDto
	 * @return
	 */
	public List<AuthDto> treegridById(AuthDto authDto);

	/**
	 * 删除权限
	 * 
	 * @param authDto
	 */
	public void delete(AuthDto authDto);

	/**
	 * 获取权限树
	 * 
	 * @param authDto
	 * @param b
	 *            是否递归子节点
	 * @return
	 */
	public List<TreeNode> tree(AuthDto authDto, boolean b);

	/**
	 * 添加权限
	 * 
	 * @param authDto
	 */
	public void add(AuthDto authDto);

	/**
	 * 编辑权限
	 * 
	 * @param authDto
	 */
	public void modify(AuthDto authDto);

}
