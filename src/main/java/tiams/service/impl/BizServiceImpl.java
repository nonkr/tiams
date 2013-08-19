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
import tiams.dto.BizAcceptDto;
import tiams.dto.BizQueryDto;
import tiams.dto.DataGrid;
import tiams.dto.BizQueryDto;
import tiams.model.Auth;
import tiams.model.Role;
import tiams.model.Roleauth;
import tiams.model.Biz;
import tiams.model.Biz;
import tiams.model.Userrole;
import tiams.service.BizServiceI;
import tiams.service.UserServiceI;
import tiams.util.Encrypt;
import tiams.util.MyException;

@Service("bizService")
public class BizServiceImpl implements BizServiceI {
    private static final Logger logger = Logger.getLogger(BizServiceImpl.class);
    
    private BaseDaoI<Biz> bizDao;

    public BaseDaoI<Biz> getUserDao() {
        return bizDao;
    }

    @Autowired
    public void setUserDao(BaseDaoI<Biz> userDao) {
        this.bizDao = userDao;
    }

    @Override
    public DataGrid datagrid(BizQueryDto bizQueryDto) {
        int page = bizQueryDto.getPage();
        int rows = bizQueryDto.getRows();
        String wtdw = bizQueryDto.getBizWtdw();
        String sort = bizQueryDto.getSort();
        String order = bizQueryDto.getOrder();
        DataGrid dg = new DataGrid();

        Map<String, Object> params = new HashMap<String, Object>();

        String hql = "from Biz b";
        hql = addHqlWhere(wtdw, params, hql);
        
        String countHql = "select count(*) " + hql;
        hql = addHqlOrder(sort, order, hql);

        List<BizQueryDto> bizDtos = copyFromModelToDto(bizDao.find(hql, params, page, rows));

        dg.setTotal(bizDao.count(countHql, params));
        dg.setRows(bizDtos);
        return dg;
    }
    
    private String addHqlWhere(String wtdw, Map<String, Object> params, String hql) {
        if (wtdw != null && !wtdw.trim().equals("")) {
            hql += " where b.bizWtdw like :wtdw";
            params.put("wtdw", "%%" + wtdw.trim() + "%%");
        }
        return hql;
    }

    private String addHqlOrder(String sort, String order, String hql) {
        if (sort != null && !sort.equals("") && order != null && !order.equals("")) {
            hql += " order by " + sort + " " + order;
        }
        return hql;
    }

    private List<BizQueryDto> copyFromModelToDto(List<Biz> bizs) {
        List<BizQueryDto> bizQueryDtos = new ArrayList<BizQueryDto>();
        if (bizs != null && bizs.size() > 0) {
            for (Biz b : bizs) {
                BizQueryDto bizQueryDto = new BizQueryDto();
                BeanUtils.copyProperties(b, bizQueryDto);
                bizQueryDtos.add(bizQueryDto);
            }
        }
        return bizQueryDtos;
    }

    @Override
    public BizAcceptDto add(BizAcceptDto bizAcceptDto) throws MyException {
        // TODO Auto-generated method stub
        return null;
    }

    
}
