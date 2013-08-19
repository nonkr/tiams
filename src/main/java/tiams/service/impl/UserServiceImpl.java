package tiams.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import tiams.dto.AuthDto;
import tiams.dto.DataGrid;
import tiams.dto.UserDto;
import tiams.model.Auth;
import tiams.model.Role;
import tiams.model.Roleauth;
import tiams.model.User;
import tiams.model.Userrole;
import tiams.service.UserServiceI;
import tiams.util.Encrypt;
import tiams.util.MyException;

@Service("userService")
public class UserServiceImpl implements UserServiceI {
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
    
    private BaseDaoI<User> userDao;

    public BaseDaoI<User> getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(BaseDaoI<User> userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDto login(UserDto userDto) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", userDto.getName());
        paramsMap.put("pwd", Encrypt.e(userDto.getPwd()));
        User u = userDao.get("from User u where u.name = :name and u.pwd = :pwd", paramsMap);
        if (u != null) {
            BeanUtils.copyProperties(u, userDto);
            return userDto;
        }
        return null;
    }

    @Override
    public DataGrid datagrid(UserDto userDto) {
        int page = userDto.getPage();
        int rows = userDto.getRows();
        String name = userDto.getName();
        String sort = userDto.getSort();
        String order = userDto.getOrder();
        DataGrid dg = new DataGrid();

        Map<String, Object> params = new HashMap<String, Object>();

        String hql = "from User u";
        hql = addHqlWhere(name, params, hql);

        String countHql = "select count(*) " + hql;
        hql = addHqlOrder(sort, order, hql);

        List<UserDto> userDtos = copyFromUserToUserDto(userDao.find(hql, params, page, rows));

        dg.setTotal(userDao.count(countHql, params));
        dg.setRows(userDtos);
        return dg;
    }

    private String addHqlWhere(String name, Map<String, Object> params, String hql) {
        if (name != null && !name.trim().equals("")) {
            hql += " where u.name like :name";
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

    private List<UserDto> copyFromUserToUserDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        if (users != null && users.size() > 0) {
            for (User u : users) {
                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(u, userDto);
                userDtos.add(userDto);
            }
        }
        return userDtos;
    }

    @Override
    public UserDto add(UserDto userDto) throws MyException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", userDto.getName());
        Long cnt = userDao.count("select count(*) from User u where u.name = :name", params);
        if(cnt > 0) {
            throw new MyException("用户名已存在！");
        } else {
            User u = new User();
            BeanUtils.copyProperties(userDto, u, new String[] { "pwd" });
            u.setId(UUID.randomUUID().toString());
            u.setCreatedatetime(new Date());
            u.setPwd(Encrypt.e(userDto.getPwd()));
            userDao.saveOrUpdate(u);
            BeanUtils.copyProperties(u, userDto);
            return userDto;
        }
    }

    @Override
    public void delete(UserDto userDto) {
        String ids = userDto.getIds();
        if(ids != null && !ids.trim().equals("")){
            userDao.execute("delete User u where u.id in (" + ids + ")");
        }
        /*
        String[] ids = userDto.getIds().split(",");
        for (String id : ids) {
            User u = userDao.get(User.class, id);
            if (u != null) {
                userDao.delete(u);
            }
        }
        */
    }

    @Override
    public UserDto modify(UserDto userDto) throws MyException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", userDto.getName());
        params.put("id", userDto.getId());
        Long cnt = userDao.count("select count(*) from User u where u.name = :name and u.id != :id", params);
        if(cnt > 0) {
            throw new MyException("用户名已存在！");
        } else {
            User u = userDao.get(User.class, userDto.getId());
            BeanUtils.copyProperties(userDto, u, new String[] { "id", "pwd" });
            u.setModifydatetime(new Date());
            if(!u.getPwd().equals(userDto.getPwd())) {
                u.setPwd(Encrypt.e(userDto.getPwd()));
            }
            BeanUtils.copyProperties(u, userDto);
            return userDto;
        }
    }
    
    /* 
     * 根据用户ID取得用户拥有的权限
     */
    @Override
    public List<AuthDto> getAuths(String id) {
        if(id == null || id.equals("")) return null;
        
        List<AuthDto> auths = new ArrayList<AuthDto>();
        User user = userDao.get(User.class, id);
        if (user != null) {
            Set<Userrole> userroles = user.getUserroles();
            if (userroles != null && userroles.size() > 0) {
                for (Userrole userrole : userroles) {
                    Role role = userrole.getRole();
                    if (role != null) {
                        Set<Roleauth> roleAuths = role.getRoleauths();
                        if (roleAuths != null && roleAuths.size() > 0) {
                            for (Roleauth roleauth : roleAuths) {
                                Auth auth = roleauth.getAuth();
                                if (auth != null) {
                                    AuthDto a = new AuthDto();
                                    a.setName(auth.getName());
                                    a.setUrl(auth.getUrl());
                                    auths.add(a);
                                }
                            }
                        }
                    }
                }
            }
        }
        return auths;
    }

    /* 
     * 根据用户对象取得用户拥有的权限
     */
    @Override
    public List<AuthDto> getAuths(UserDto u) {
        if(u == null || u.getId() == null || u.getId().equals("")) return null;
        return getAuths(u.getId());
    }

}
