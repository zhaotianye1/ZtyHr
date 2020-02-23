package org.zty.ztyspringboothr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zty.ztyspringboothr.bean.Department;
import org.zty.ztyspringboothr.mapper.DepartmentMapper;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/5 18:23
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }

    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments();
    }
}
