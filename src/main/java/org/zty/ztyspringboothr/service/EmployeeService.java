<<<<<<< HEAD
package org.zty.ztyspringboothr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zty.ztyspringboothr.bean.Employee;
import org.zty.ztyspringboothr.bean.RespPageBean;
import org.zty.ztyspringboothr.mapper.EmployeeMapper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

/**
 * @author 赵天野
 * @date 2020/2/13 14:27
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
    SimpleDateFormat yearFormat =new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat =new SimpleDateFormat("MM");
    DecimalFormat decimalFormat=new DecimalFormat("##.00");

    public RespPageBean getEmployeeByPage(Integer page, Integer size,Employee employee,Date[] beginDateScope) {
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> data=employeeMapper.getEmployeeByPage(page,size,employee,beginDateScope);
        Long total=employeeMapper.getTotal(employee,beginDateScope);
        RespPageBean bean=new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    public Integer addEmp(Employee employee) {
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        double month = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12 + (Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
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
=======
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
>>>>>>> d183975e40851db894f2de6f5eb0421bd29ad4e4
