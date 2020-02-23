package org.zty.ztyspringboothr.controller.system.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zty.ztyspringboothr.bean.Menu;
import org.zty.ztyspringboothr.bean.RespBean;
import org.zty.ztyspringboothr.bean.Role;
import org.zty.ztyspringboothr.service.MenuService;
import org.zty.ztyspringboothr.service.PermissService;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/1/31 14:05
 */
@RestController
@RequestMapping("/system/basic/permiss/")
public class PermissController {
    @Autowired
    PermissService permissService;
    @Autowired
    MenuService menuService;
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return permissService.getAllRoles();
    }

    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }

    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        if(menuService.updateMenuRole(rid,mids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if(permissService.addRole(role)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable("rid") Integer id){
        if(permissService.deleteRoleById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
