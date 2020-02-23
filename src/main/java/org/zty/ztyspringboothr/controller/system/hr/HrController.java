package org.zty.ztyspringboothr.controller.system.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zty.ztyspringboothr.bean.Hr;
import org.zty.ztyspringboothr.bean.RespBean;
import org.zty.ztyspringboothr.bean.Role;
import org.zty.ztyspringboothr.service.HrService;
import org.zty.ztyspringboothr.service.PermissService;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/7 15:30
 */
@RestController
@RequestMapping("/system/hr/")
public class HrController {

    @Autowired
    HrService hrService;

    @Autowired
    PermissService permissService;

    @GetMapping("/")
    public List<Hr> getAllHrs(String keywords){
        return hrService.getAllHrs(keywords);
    }

    @PutMapping("/")
    public RespBean updateHr(@RequestBody  Hr hr){
        if (hrService.updateHr(hr)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @GetMapping("/role")
    public List<Role> getAllRoles(){
        return permissService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid,Integer[] rids){
        if(hrService.updateHrRole(hrid,rids)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteById(@PathVariable Integer id){
        if(hrService.deleteById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
