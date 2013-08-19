package tiams.service.impl;

import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tiams.model.Auth;
import tiams.model.Menu;
import tiams.model.Role;
import tiams.model.Roleauth;
import tiams.model.User;
import tiams.model.Userrole;
import tiams.service.RepairServiceI;
import tiams.util.Encrypt;
import tiams.dao.BaseDaoI;

@Service("repairService")
public class RepairServiceImpl implements RepairServiceI {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(RepairServiceImpl.class);

    private BaseDaoI<Menu> menuDao;
    private BaseDaoI<User> userDao;
    private BaseDaoI<Userrole> userroleDao;
    private BaseDaoI<Role> roleDao;
    private BaseDaoI<Roleauth> roleauthDao;
    private BaseDaoI<Auth> authDao;

    public BaseDaoI<Menu> getMenuDao() {
        return menuDao;
    }

    @Autowired
    public void setMenuDao(BaseDaoI<Menu> menuDao) {
        this.menuDao = menuDao;
    }

    public BaseDaoI<User> getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(BaseDaoI<User> userDao) {
        this.userDao = userDao;
    }

    public BaseDaoI<Userrole> getUserroleDao() {
        return userroleDao;
    }

    @Autowired
    public void setUserroleDao(BaseDaoI<Userrole> userroleDao) {
        this.userroleDao = userroleDao;
    }

    public BaseDaoI<Role> getRoleDao() {
        return roleDao;
    }

    @Autowired
    public void setRoleDao(BaseDaoI<Role> roleDao) {
        this.roleDao = roleDao;
    }

    public BaseDaoI<Roleauth> getRoleauthDao() {
        return roleauthDao;
    }

    @Autowired
    public void setRoleauthDao(BaseDaoI<Roleauth> roleauthDao) {
        this.roleauthDao = roleauthDao;
    }

    public BaseDaoI<Auth> getAuthDao() {
        return authDao;
    }

    @Autowired
    public void setAuthDao(BaseDaoI<Auth> authDao) {
        this.authDao = authDao;
    }

    @Override
    synchronized public void repair() {
        repairMenu();// 修复菜单

        repairUser();// 修复用户

        repairRole();// 修复角色

        repairUserRole();// 修复用户和角色的关系

        repairAuth();// 修复权限

        repairRoleAuth();// 修复角色和权限的关系
    }

    private void repairMenu() {
        Menu root = new Menu();
        root.setId("0");
        root.setText("首页");
        root.setUrl("");
        root.setSeq(0);
        menuDao.saveOrUpdate(root);

        Menu xtgl = new Menu();
        xtgl.setId("xtgl");
//        xtgl.setMenu(root);
        xtgl.setText("系统管理");
        xtgl.setUrl("");
        xtgl.setSeq(1);
        menuDao.saveOrUpdate(xtgl);

        Menu yhgl = new Menu();
        yhgl.setId("yhgl");
        yhgl.setMenu(xtgl);
        yhgl.setText("用户管理");
        yhgl.setUrl("/modules/sys/sys_user.jsp");
        yhgl.setSeq(0);
        menuDao.saveOrUpdate(yhgl);

        Menu jsgl = new Menu();
        jsgl.setId("jsgl");
        jsgl.setMenu(xtgl);
        jsgl.setText("角色管理");
        jsgl.setUrl("/modules/sys/sys_role.jsp");
        jsgl.setSeq(1);
        menuDao.saveOrUpdate(jsgl);

        Menu qxgl = new Menu();
        qxgl.setId("qxgl");
        qxgl.setMenu(xtgl);
        qxgl.setText("权限管理");
        qxgl.setUrl("/modules/sys/sys_auth.jsp");
        qxgl.setSeq(2);
        menuDao.saveOrUpdate(qxgl);


        Menu ywgl = new Menu();
        ywgl.setId("ywgl");
        ywgl.setText("业务管理");
        ywgl.setSeq(2);
        menuDao.saveOrUpdate(ywgl);

        Menu ywsl = new Menu();
        ywsl.setId("ywsl");
        ywsl.setMenu(ywgl);
        ywsl.setText("业务受理");
        ywsl.setUrl("/modules/biz/biz_ywsl.jsp");
        ywsl.setSeq(0);
        menuDao.saveOrUpdate(ywsl);

        Menu ywcx = new Menu();
        ywcx.setId("ywcx");
        ywcx.setMenu(ywgl);
        ywcx.setText("业务查询");
        ywcx.setUrl("/modules/biz/biz_ywcx.jsp");
        ywcx.setSeq(1);
        menuDao.saveOrUpdate(ywcx);
        
//        Menu xmfj = new Menu();
//        xmfj.setId("xmfj");
//        xmfj.setText("项目分解");
//        xmfj.setUrl("/modules/xmfj.jsp");
//        xmfj.setSeq(3);
//        menuDao.saveOrUpdate(xmfj);
        
        Menu rwfp = new Menu();
        rwfp.setId("rwfp");
        rwfp.setText("任务分派");
        rwfp.setUrl("/modules/rwfp.jsp");
        rwfp.setSeq(4);
        menuDao.saveOrUpdate(rwfp);
        
        Menu xmjy = new Menu();
        xmjy.setId("xmjy");
        xmjy.setText("项目检验");
        xmjy.setSeq(5);
        menuDao.saveOrUpdate(xmjy);
        
        Menu jytj = new Menu();
        jytj.setId("jytj");
        jytj.setMenu(xmjy);
        jytj.setText("检验提交");
        jytj.setUrl("/modules/inspect/jytj.jsp");
        jytj.setSeq(0);
        menuDao.saveOrUpdate(jytj);
        
        Menu jysh = new Menu();
        jysh.setId("jysh");
        jysh.setMenu(xmjy);
        jysh.setText("检验审核");
        jysh.setUrl("/modules/inspect/jysh.jsp");
        jysh.setSeq(1);
        menuDao.saveOrUpdate(jysh);
        
        Menu bgbz = new Menu();
        bgbz.setId("bgbz");
        bgbz.setText("报告编制");
        bgbz.setUrl("/modules/bgbz.jsp");
        bgbz.setSeq(6);
        menuDao.saveOrUpdate(bgbz);
        
        Menu bzkgl = new Menu();
        bzkgl.setId("bzkgl");
        bzkgl.setText("标准库管理");
        bzkgl.setSeq(7);
        menuDao.saveOrUpdate(bzkgl);
        
        Menu cfjybzk = new Menu();
        cfjybzk.setId("cfjybzk");
        cfjybzk.setMenu(bzkgl);
        cfjybzk.setText("成分检验标准库");
        cfjybzk.setSeq(0);
        menuDao.saveOrUpdate(cfjybzk);
        
        Menu jxjybzk = new Menu();
        jxjybzk.setId("jxjybzk");
        jxjybzk.setMenu(bzkgl);
        jxjybzk.setText("金相检验标准库");
        jxjybzk.setSeq(1);
        menuDao.saveOrUpdate(jxjybzk);
        
        Menu ydjybzk = new Menu();
        ydjybzk.setId("ydjybzk");
        ydjybzk.setMenu(bzkgl);
        ydjybzk.setText("硬度检验标准库");
        ydjybzk.setSeq(2);
        menuDao.saveOrUpdate(ydjybzk);
        
        Menu jldjybzk = new Menu();
        jldjybzk.setId("jldjybzk");
        jldjybzk.setMenu(bzkgl);
        jldjybzk.setText("晶粒度检验标准库");
        jldjybzk.setSeq(3);
        menuDao.saveOrUpdate(jldjybzk);
        
        Menu grcdjybzk = new Menu();
        grcdjybzk.setId("grcdjybzk");
        grcdjybzk.setMenu(bzkgl);
        grcdjybzk.setText("过热程度检验标准库");
        grcdjybzk.setSeq(4);
        menuDao.saveOrUpdate(grcdjybzk);
        
        Menu hhcdjybzk = new Menu();
        hhcdjybzk.setId("hhcdjybzk");
        hhcdjybzk.setMenu(bzkgl);
        hhcdjybzk.setText("回火程度检验标准库");
        hhcdjybzk.setSeq(5);
        menuDao.saveOrUpdate(hhcdjybzk);
        
    }

    private void repairUser() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("name", "admin");
        User t = userDao.get("from User u where u.name = :name and u.id != '0'", m);
        if (t != null) {
            t.setName(UUID.randomUUID().toString());
        }

        User admin = new User();
        admin.setId("0");
        admin.setName("admin");
        admin.setPwd(Encrypt.e("admin"));
        admin.setModifydatetime(new Date());
        userDao.saveOrUpdate(admin);
    }

    private void repairRole() {
        Role admin = new Role();
        admin.setId("0");
        admin.setName("超级管理员");
        admin.setRemark("拥有系统所有权限");
        roleDao.saveOrUpdate(admin);

        Role guest = new Role();
        guest.setId("1");
        guest.setName("Guest");
        guest.setRemark("");
        roleDao.saveOrUpdate(guest);

        Role test = new Role();
        test.setId("2");
        test.setName("测试工程师");
        test.setRemark("");
        roleDao.saveOrUpdate(test);
    }

    /**
     * 修复用户所属的角色
     */
    private void repairUserRole() {
        userroleDao.execute("delete Userrole t where t.user.id in ( '0' )");

        Userrole userrole = new Userrole();
        userrole.setId(UUID.randomUUID().toString());
        userrole.setRole(roleDao.get(Role.class, "0"));
        userrole.setUser(userDao.get(User.class, "0"));
        userroleDao.save(userrole);
    }

    /**
     * 修复权限列表
     */
    private void repairAuth() {
        authDao.execute("update Auth a set a.auth = null");

        Auth home = new Auth();
        home.setId("0");
        home.setAuth(null);
        home.setName("首页");
        home.setUrl("");
        home.setSeq(1);
        home.setRemark("");
        authDao.saveOrUpdate(home);

        Auth xtgl = new Auth();
        xtgl.setId("xtgl");
        xtgl.setAuth(home);
        xtgl.setName("系统管理");
        xtgl.setUrl("");
        xtgl.setSeq(1);
        xtgl.setRemark("");
        authDao.saveOrUpdate(xtgl);

        Auth yhgl = new Auth();
        yhgl.setId("yhgl");
        yhgl.setAuth(xtgl);
        yhgl.setName("用户管理");
        yhgl.setUrl("");
        yhgl.setSeq(1);
        yhgl.setRemark("");
        authDao.saveOrUpdate(yhgl);

        Auth yhglym = new Auth();
        yhglym.setId("yhglym");
        yhglym.setAuth(yhgl);
        yhglym.setName("用户管理页面");
        yhglym.setUrl("/sys/userAction!user.action");
        yhglym.setSeq(1);
        yhglym.setRemark("");
        authDao.saveOrUpdate(yhglym);

        Auth yhcx = new Auth();
        yhcx.setId("yhcx");
        yhcx.setAuth(yhgl);
        yhcx.setName("用户查询");
        yhcx.setUrl("/sys/userAction!datagrid.action");
        yhcx.setSeq(2);
        yhcx.setRemark("");
        authDao.saveOrUpdate(yhcx);

        Auth yhadd = new Auth();
        yhadd.setId("yhadd");
        yhadd.setAuth(yhgl);
        yhadd.setName("用户添加");
        yhadd.setUrl("/sys/userAction!add.action");
        yhadd.setSeq(3);
        yhadd.setRemark("");
        authDao.saveOrUpdate(yhadd);

        Auth yhedit = new Auth();
        yhedit.setId("yhedit");
        yhedit.setAuth(yhgl);
        yhedit.setName("用户修改");
        yhedit.setUrl("/sys/userAction!modify.action");
        yhedit.setSeq(4);
        yhedit.setRemark("");
        authDao.saveOrUpdate(yhedit);

        Auth yhsc = new Auth();
        yhsc.setId("yhsc");
        yhsc.setAuth(yhgl);
        yhsc.setName("用户删除");
        yhsc.setUrl("/sys/userAction!delete.action");
        yhsc.setSeq(5);
        yhsc.setRemark("");
        authDao.saveOrUpdate(yhsc);

        // Auth yhxgmm = new Auth();
        // yhxgmm.setId("yhxgmm");
        // yhxgmm.setAuth(yhgl);
        // yhxgmm.setName("修改密码");
        // yhxgmm.setUrl("/sys/userAction!modifyPwd.action");
        // yhxgmm.setSeq(6);
        // yhxgmm.setRemark("批量修改用户密码");
        // authDao.saveOrUpdate(yhxgmm);

        Auth yhxgjs = new Auth();
        yhxgjs.setId("yhxgjs");
        yhxgjs.setAuth(yhgl);
        yhxgjs.setName("修改角色");
        yhxgjs.setUrl("/sys/userAction!modifyUserRole.action");
        yhxgjs.setSeq(7);
        yhxgjs.setRemark("批量修改用户角色");
        authDao.saveOrUpdate(yhxgjs);

        Auth jsgl = new Auth();
        jsgl.setId("jsgl");
        jsgl.setAuth(xtgl);
        jsgl.setName("角色管理");
        jsgl.setUrl("");
        jsgl.setSeq(2);
        jsgl.setRemark("");
        authDao.saveOrUpdate(jsgl);

        Auth jsglym = new Auth();
        jsglym.setId("jsglym");
        jsglym.setAuth(jsgl);
        jsglym.setName("角色管理页面");
        jsglym.setUrl("/roleAction!role.action");
        jsglym.setSeq(1);
        jsglym.setRemark("");
        authDao.saveOrUpdate(jsglym);

        Auth jscx = new Auth();
        jscx.setId("jscx");
        jscx.setAuth(jsgl);
        jscx.setName("角色查询");
        jscx.setUrl("/roleAction!datagrid.action");
        jscx.setSeq(2);
        jscx.setRemark("");
        authDao.saveOrUpdate(jscx);

        Auth jsadd = new Auth();
        jsadd.setId("jsadd");
        jsadd.setAuth(jsgl);
        jsadd.setName("角色添加");
        jsadd.setUrl("/roleAction!add.action");
        jsadd.setSeq(3);
        jsadd.setRemark("");
        authDao.saveOrUpdate(jsadd);

        Auth jsedit = new Auth();
        jsedit.setId("jsedit");
        jsedit.setAuth(jsgl);
        jsedit.setName("角色编辑");
        jsedit.setUrl("/roleAction!edit.action");
        jsedit.setSeq(4);
        jsedit.setRemark("");
        authDao.saveOrUpdate(jsedit);

        Auth jsdelete = new Auth();
        jsdelete.setId("jsdelete");
        jsdelete.setAuth(jsgl);
        jsdelete.setName("角色删除");
        jsdelete.setUrl("/roleAction!delete.action");
        jsdelete.setSeq(5);
        jsdelete.setRemark("");
        authDao.saveOrUpdate(jsdelete);

        Auth qxgl = new Auth();
        qxgl.setId("qxgl");
        qxgl.setAuth(xtgl);
        qxgl.setName("权限管理");
        qxgl.setUrl("");
        qxgl.setSeq(3);
        qxgl.setRemark("");
        authDao.saveOrUpdate(qxgl);

        Auth qxglym = new Auth();
        qxglym.setId("qxglym");
        qxglym.setAuth(qxgl);
        qxglym.setName("权限管理页面");
        qxglym.setUrl("/authAction!auth.action");
        qxglym.setSeq(1);
        qxglym.setRemark("");
        authDao.saveOrUpdate(qxglym);

        Auth qxcx = new Auth();
        qxcx.setId("qxcx");
        qxcx.setAuth(qxgl);
        qxcx.setName("权限查询");
        qxcx.setUrl("/authAction!treegrid.action");
        qxcx.setSeq(2);
        qxcx.setRemark("");
        authDao.saveOrUpdate(qxcx);

        Auth qxadd = new Auth();
        qxadd.setId("qxadd");
        qxadd.setAuth(qxgl);
        qxadd.setName("权限添加");
        qxadd.setUrl("/authAction!add.action");
        qxadd.setSeq(3);
        qxadd.setRemark("");
        authDao.saveOrUpdate(qxadd);

        Auth qxedit = new Auth();
        qxedit.setId("qxedit");
        qxedit.setAuth(qxgl);
        qxedit.setName("权限编辑");
        qxedit.setUrl("/authAction!edit.action");
        qxedit.setSeq(4);
        qxedit.setRemark("");
        authDao.saveOrUpdate(qxedit);

        Auth qxdelete = new Auth();
        qxdelete.setId("qxdelete");
        qxdelete.setAuth(qxgl);
        qxdelete.setName("权限删除");
        qxdelete.setUrl("/authAction!delete.action");
        qxdelete.setSeq(5);
        qxdelete.setRemark("");
        authDao.saveOrUpdate(qxdelete);

    }

    /**
     * 修复角色拥有的权限
     */
    private void repairRoleAuth() {
        roleauthDao.execute("delete Roleauth r where r.role.id = '0'");

        Role role = roleDao.get(Role.class, "0");

        List<Auth> auths = authDao.find("from Auth");
        if (auths != null && auths.size() > 0) {
            for (Auth auth : auths) {
                Roleauth roleauth = new Roleauth();
                roleauth.setId(UUID.randomUUID().toString());
                roleauth.setRole(role);
                roleauth.setAuth(auth);
                roleauthDao.save(roleauth);
            }
        }
    }
}
