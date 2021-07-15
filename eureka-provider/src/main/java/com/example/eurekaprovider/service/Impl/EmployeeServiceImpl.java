package com.example.eurekaprovider.service.Impl;

import com.example.commons.entity.Employee;
import com.example.eurekaprovider.dao.EmployeeMapper;
import com.example.eurekaprovider.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 19:17
 **/
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public Employee findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public List<Employee> findByIds(List<Long> ids) {
        return employeeMapper.findByIds(ids);
    }

    @Override
    public int insertBatch(List<Employee> employees) {
        return employeeMapper.insertBatch(employees);
    }
}
