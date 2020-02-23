package org.zty.ztyspringboothr.controller.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zty.ztyspringboothr.bean.*;
import org.zty.ztyspringboothr.mapper.DepartmentMapper;
import org.zty.ztyspringboothr.service.*;
import org.zty.ztyspringboothr.utils.PoiUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/13 14:20
 */
@RestController
@RequestMapping("/employee/basic/")
public class EmpBasicController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    NationService nationService;

    @Autowired
    PoliticsstatusService politicsstatusService;


    @Autowired
    JobLevelService jobLevelService;


    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentMapper departmentMapper;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size,Employee employee,Date[] beginDateScope){
        return employeeService.getEmployeeByPage(page,size,employee,beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody  Employee employee){
        if(employeeService.addEmp(employee)==1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @GetMapping("/nation")
    public List<Nation> getAllNations(){
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevel")
    public List<JobLevel> getAllJobLevels(){
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/position")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @GetMapping("/maxworkid")
    public String maxWordId(){
        return String.format("%08d",employeeService.maxWordId()+1);
    }

    @GetMapping("/dep")
    public List<Department> getAllDepartments(){
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpById(@PathVariable Integer id){
        if(employeeService.deleteEmpById(id)==1){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateEmp(employee)==1){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @GetMapping("export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> list= (List<Employee>) employeeService.getEmployeeByPage(null,null,null,null).getData();
        return PoiUtils.employee2Excel(list);
    }

//    @PostMapping("/import")
//    public RespBean importData(MultipartFile file) throws IOException {
//        List<Employee> list=PoiUtils.excel2Employee(file,nationService.getAllNations(),politicsstatusService.getAllPoliticsstatus(),departmentService.getAllDepartments(),positionService.getAllPositions(),jobLevelService.getAllJobLevels());
//        return RespBean.ok("上传成功！");
//    }
}
