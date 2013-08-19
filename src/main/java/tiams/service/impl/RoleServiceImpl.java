package tiams.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiams.dao.BaseDaoI;
import tiams.dto.DataGrid;
import tiams.dto.RoleDto;
import tiams.model.Auth;
import tiams.model.Role;
import tiams.model.Roleauth;
import tiams.model.Userrole;
import tiams.service.RoleServiceI;
import tiams.util.MyException;

@Service("roleService")
public class RoleServiceImpl implements RoleServiceI {
    private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);

    private BaseDaoI<Role> roleDao;
    private BaseDaoI<Auth> authDao;
    private BaseDaoI<Roleauth> roleauthDao;
    private BaseDaoI<Userrole> userroleDao;

    public BaseDaoI<Userrole> getUserroleDao() {
        return userroleDao;
    }

    @Autowired
    public void setUserroleDao(BaseDaoI<Userrole> userroleDao) {
        this.userroleDao = userroleDao;
    }

    public BaseDaoI<Auth> getAuthDao() {
        return authDao;
    }

    @Autowired
    public void setAuthDao(BaseDaoI<Auth> authDao) {
        this.authDao = authDao;
    }

    public BaseDaoI<Roleauth> getRoleauthDao() {
        return roleauthDao;
    }

    @Autowired
    public void setRoleauthDao(BaseDaoI<Roleauth> roleauthDao) {
        this.roleauthDao = roleauthDao;
    }

    public BaseDaoI<Role> getRoleDao() {
        return roleDao;
    }

    @Autowired
    public void setRoleDao(BaseDaoI<Role> roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public DataGrid datagrid(RoleDto roleDto) {
        int page = roleDto.getPage();
        int rows = roleDto.getRows();
        String name = roleDto.getName();
        String sort = roleDto.getSort();
        String order = roleDto.getOrder();
        DataGrid dg = new DataGrid();

        Map<String, Object> params = new HashMap<String, Object>();

        String hql = "from Role r";
        hql = addHqlWhere(name, params, hql);

        String countHql = "select count(*) " + hql;
        hql = addHqlOrder(sort, order, hql);

        List<Role> roles = roleDao.find(hql, params, page, rows);

        List<RoleDto> roleDtos = new ArrayList<RoleDto>();
        if (roles != null && roles.size() > 0) {
            for (Role r : roles) {
                RoleDto rd = new RoleDto();
                BeanUtils.copyProperties(r, rd);

                Set<Roleauth> roleauths = r.getRoleauths();
                String authIds = "";
                String authNames = "";
                if (roleauths != null && roleauths.size() > 0) {
                    for (Roleauth ra : roleauths) {
                        authIds += "," + ra.getAuth().getId();
                        authNames += "," + ra.getAuth().getName();
                    }
                }
                if (authIds.equals("")) {
                    rd.setAuthIds("");
                    rd.setAuthNames("");
                } else {
                    rd.setAuthIds(authIds.substring(1));
                    rd.setAuthNames(authNames.substring(1));
                }

                roleDtos.add(rd);
            }
        }

        dg.setTotal(roleDao.count(countHql, params));
        dg.setRows(roleDtos);
        return dg;
    }

    private String addHqlWhere(String name, Map<String, Object> params, String hql) {
        if (name != null && !name.trim().equals("")) {
            hql += " where r.name like :name";
            params.put("name", "%%" + name.trim() + "%%");
        }
        return hql;
    }

    private String addHqlOrder(String sort, String order, String hql) {
        if (sort != null && !sort.equals("") && order != null && !order.equals("")) {
            hql += " order by " + sort + " " + order;
        }
        return hql;
    }

    @Override
    public RoleDto add(RoleDto roleDto) throws MyException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", roleDto.getName());
        Long cnt = roleDao.count("select count(*) from Role r where r.name = :name",
                params);
        if (cnt > 0) {
            throw new MyException("角色名已存在！");
        } else {
            Role r = new Role();
            BeanUtils.copyProperties(roleDto, r);
            r.setId(UUID.randomUUID().toString());
            roleDao.save(r);
            BeanUtils.copyProperties(r, roleDto);

            return saveRoleAuth(roleDto, r);
        }
    }

    /**
     * 保存Role和Auth的关系
     * 
     * @param roleDto
     * @param r
     */
    private RoleDto saveRoleAuth(RoleDto roleDto, Role r) {
        if (roleDto.getAuthIds() != null) {
            String authNames = "";
            for (String id : roleDto.getAuthIds().split(",")) {
                Roleauth roleauth = new Roleauth();
                roleauth.setId(UUID.randomUUID().toString());
                Auth auth = authDao.get(Auth.class, id.trim());
                authNames += "," + auth.getName();
                roleauth.setAuth(auth);
                roleauth.setRole(r);
                roleauthDao.save(roleauth);
            }
            roleDto.setAuthNames(authNames.substring(1));
        } else {
            roleDto.setAuthIds("");
            roleDto.setAuthNames("");
        }

        return roleDto;
    }

    @Override
    public void delete(RoleDto roleDto) {
        String ids = roleDto.getIds();
        if (ids != null && !ids.trim().equals("")) {
            roleauthDao.execute("delete Roleauth t where t.role.id in (" + ids + ")");
            userroleDao.execute("delete Userrole t where t.role.id in (" + ids + ")");
            roleDao.execute("delete Role r where r.id in (" + ids + ")");
        }
    }

    @Override
    public RoleDto modify(RoleDto roleDto) throws MyException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", roleDto.getName());
        params.put("id", roleDto.getId());
        Long cnt = roleDao.count(
                "select count(*) from Role r where r.name = :name and r.id != :id",
                params);
        if (cnt > 0) {
            throw new MyException("角色名已存在！");
        } else {
            Role r = roleDao.get(Role.class, roleDto.getId());
            BeanUtils.copyProperties(roleDto, r, new String[] { "id" });
            BeanUtils.copyProperties(r, roleDto);

            Map<String, Object> params2 = new HashMap<String, Object>();
            params2.put("role", r);
            roleauthDao.execute("delete Roleauth r where r.role = :role", params2);
            return saveRoleAuth(roleDto, r);
        }
    }

}
