package org.zty.ztyspringboothr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zty.ztyspringboothr.bean.Hr;
import org.zty.ztyspringboothr.bean.Menu;
import org.zty.ztyspringboothr.mapper.MenuMapper;
import org.zty.ztyspringboothr.mapper.MenuRoleMapper;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/1/4 10:02
 */
@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;
    public List<Menu> getMenuByHrId() {
        return menuMapper.getMenuByHrId(((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());

    }

    //@Cacheable
    public List<Menu> getAllMenusWithRole(){
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.deleteById(rid);
        if(mids==null || mids.length==0){
            return true;
        }else {
        Integer result=menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
        }
    }
}
