package org.zty.ztyspringboothr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zty.ztyspringboothr.bean.Employee;
import org.zty.ztyspringboothr.bean.RespPageBean;
import org.zty.ztyspringboothr.mapper.EmployeeMapper;

import java.util.List;

/**
 * @author 赵天野
 * @date 2020/2/13 14:27
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public RespPageBean getEmployeeByPage(Integer page, Integer size,String keyword) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> data=employeeMapper.getEmployeeByPage(page,size,keyword);
        Long total=employeeMapper.getTotal(keyword);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }

    public Integer maxWordId() {
        return employeeMapper.maxWorkId();
    }

    public Integer deleteEmpById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }
}
