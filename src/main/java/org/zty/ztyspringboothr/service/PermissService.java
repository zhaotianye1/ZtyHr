package org.zty.ztyspringboothr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zty.ztyspringboothr.bean.Role;
import org.zty.ztyspringboothr.mapper.RoleMapper;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/1/31 14:07
 */
@Service
public class PermissService {
    @Autowired
    RoleMapper roleMapper;
    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public Integer addRole(Role role) {
        if(!role.getName().startsWith("Role_")){
            role.setName("Role_"+role.getName());
        }
        return roleMapper.insert(role);
    }

    public Integer deleteRoleById(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }
}
