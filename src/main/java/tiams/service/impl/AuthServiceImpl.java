package tiams.service.impl;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tiams.comparator.AuthComparator;
import tiams.dao.BaseDaoI;
import tiams.dto.AuthDto;
import tiams.dto.TreeNode;
import tiams.model.Auth;
import tiams.service.AuthServiceI;

/**
 * 权限Service
 * 
 * @author nonkr
 * 
 */
@Service("authService")
public class AuthServiceImpl implements AuthServiceI {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class);

	private BaseDaoI<Auth> authDao;

	public BaseDaoI<Auth> getAuthDao() {
        return authDao;
    }

	@Autowired
    public void setAuthDao(BaseDaoI<Auth> authDao) {
        this.authDao = authDao;
    }

    /**
	 * 获得权限treegrid
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AuthDto> treegrid() {
		List<Auth> auths;
		auths = authDao.find("from Auth t order by t.seq");
		
		List<AuthDto> authDtos = new ArrayList<AuthDto>();
        if (auths != null && auths.size() > 0) {
            for (Auth t : auths) {
                AuthDto a = new AuthDto();
                BeanUtils.copyProperties(t, a);
                if (t.getAuth() != null) {
                    a.setPid(t.getAuth().getId());
                    a.setPname(t.getAuth().getName());
                }
                authDtos.add(a);
            }
        }
		return authDtos;
	}
	
	/**
     * 获得权限treegridById
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<AuthDto> treegridById(AuthDto auth) {
        List<Auth> auths;
        if (auth != null && auth.getId() != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", auth.getId());
            auths = authDao.find("from Auth t where t.auth.id = :id order by t.seq", params);
        } else {
            auths = authDao.find("from Auth t where t.auth is null order by t.seq");
        }
        return getAuthsFromTauths(auths);
    }

	private List<AuthDto> getAuthsFromTauths(List<Auth> tauths) {
		List<AuthDto> auths = new ArrayList<AuthDto>();
		if (tauths != null && tauths.size() > 0) {
			for (Auth t : tauths) {
				AuthDto a = new AuthDto();
				BeanUtils.copyProperties(t, a);
				if (t.getAuth() != null) {
					a.setPid(t.getAuth().getId());
					a.setPname(t.getAuth().getName());
				}
				if (countChildren(t.getId()) > 0) {
					a.setState("closed");
				}
				auths.add(a);
			}
		}
		return auths;
	}

	private Long countChildren(String pid) {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", pid);
		return authDao.count("select count(*) from Auth t where t.auth.id = :id", params);
	}

	public void delete(AuthDto auth) {
		del(auth.getId());
	}

	private void del(String id) {
		Auth t = authDao.get(Auth.class, id);
		if (t != null) {
		    Map<String, Object> params = new HashMap<String, Object>();
		    params.put("auth", t);
			authDao.execute("delete Roleauth r where r.auth = :auth", params);
			Set<Auth> auths = t.getAuths();
			if (auths != null && !auths.isEmpty()) {
				// 说明当前权限下面还有子权限
			    auths.remove(t);
			    t.setAuths(null);
				for (Auth auth : auths) {
					del(auth.getId());
				}
			}
			authDao.delete(t);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TreeNode> tree(AuthDto auth, boolean b) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Auth t where t.auth is null order by t.seq";
		if (auth != null && auth.getId() != null && !auth.getId().trim().equals("")) {
			hql = "from Auth t where t.auth.id = :id order by t.seq";
			params.put("id", auth.getId());
		}
		
		List<Auth> l = authDao.find(hql, params);
		List<TreeNode> treeNode = new ArrayList<TreeNode>();
		for (Auth t : l) {
			treeNode.add(tree(t, b));
		}
		return treeNode;
	}

	private TreeNode tree(Auth t, boolean recursive) {
		TreeNode node = new TreeNode();
		node.setId(t.getId());
		node.setText(t.getName());
		Map<String, Object> attributes = new HashMap<String, Object>();
		node.setAttributes(attributes);
		if (t.getAuths() != null && t.getAuths().size() > 0) {
			node.setState("open");
			if (recursive) {// 递归查询子节点
				List<Auth> l = new ArrayList<Auth>(t.getAuths());
				Collections.sort(l, new AuthComparator());// 排序
				List<TreeNode> children = new ArrayList<TreeNode>();
				for (Auth r : l) {
					TreeNode tn = tree(r, true);
					children.add(tn);
				}
				node.setChildren(children);
			}
		}
		return node;
	}

	public void add(AuthDto authDto) {
		Auth a = new Auth();
		BeanUtils.copyProperties(authDto, a);
		if (authDto.getPid() != null && !authDto.getPid().equals(authDto.getId())) {
			a.setAuth(authDao.get(Auth.class, authDto.getPid()));
		}
		authDao.save(a);
	}

	public void modify(AuthDto authDto) {
	    logger.info("modify");
		Auth a = authDao.get(Auth.class, authDto.getId());// 要修改的权限
		BeanUtils.copyProperties(authDto, a);
		if (authDto.getPid() != null && !authDto.getPid().equals(authDto.getId())) {
			Auth pAuth = authDao.get(Auth.class, authDto.getPid());// 要设置的上级权限
			if (pAuth != null) {
				if (isDown(a, pAuth)) {// 要将当前节点修改到当前节点的子节点中
					Set<Auth> tauths = a.getAuths();// 当前要修改的权限的所有下级权限
					if (tauths != null && tauths.size() > 0) {
						for (Auth auth : tauths) {
							if (auth != null) {
								auth.setAuth(null);
							}
						}
					}
				}
				a.setAuth(pAuth);
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 * @param pt
	 * @return
	 */
	private boolean isDown(Auth t, Auth pt) {
		if (pt.getAuth() != null) {
			if (pt.getAuth().getId().equals(t.getAuth())) {
				return true;
			} else {
				return isDown(t, pt.getAuth());
			}
		}
		return false;
	}

}
