package com.example.eurekaprovider.service;

import com.example.commons.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 14:29
 **/

@Service
public interface EmployeeService {

    public Employee findById(Long id);

    public List<Employee> findByIds(List<Long> ids);

    public int insertBatch(List<Employee> employees);
}
