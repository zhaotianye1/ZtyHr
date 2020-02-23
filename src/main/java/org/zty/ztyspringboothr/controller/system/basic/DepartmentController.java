package org.zty.ztyspringboothr.controller.system.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zty.ztyspringboothr.bean.Department;
import org.zty.ztyspringboothr.bean.RespBean;
import org.zty.ztyspringboothr.mapper.DepartmentMapper;
import org.zty.ztyspringboothr.service.DepartmentService;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/5 15:10
 */
@RestController
@RequestMapping("/system/basic/department/")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    DepartmentMapper departmentMapper;

    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @PostMapping("/")
    public RespBean addDep(@RequestBody  Department dep){
        departmentService.addDep(dep);
        if(dep.getResult()==1){
            return RespBean.ok("添加成功！",dep);
        }
        return RespBean.error("添加失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id){
        Department department=new Department();
        department.setId(id);
        departmentService.deleteDepById(department);
        if(department.getResult()==-2){
            return RespBean.error("此部门下有子部门，删除失败！");
        }else if(department.getResult()==-1){
            return RespBean.error("该部门下有员工，删除失败！");
        }else if(department.getResult()==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.ok("删除失败！");
    }
}
