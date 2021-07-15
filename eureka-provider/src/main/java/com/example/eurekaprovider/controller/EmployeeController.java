package com.example.eurekaprovider.controller;

import com.example.commons.entity.CommonResult;
import com.example.commons.entity.Employee;
import com.example.eurekaprovider.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: guoqing.chen01@hand-china.com 2021-07-14 11:01
 **/

@RestController
@Api(tags = "员工管理相关接口")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/findById/{id}")
    @ApiOperation("员工查找接口")
    @ResponseBody
    public CommonResult<Employee> findById(@PathVariable Long id) {
        return new CommonResult<>(200,"request success",employeeService.findById(id));
    }

    @PostMapping("/findByIds")
    @ApiOperation("员工批量查找接口")
    @ResponseBody
    public List<Employee> findByIds(@RequestBody List<Long> ids) {
        return employeeService.findByIds(ids);
    }

    @PostMapping("/insertBatch")
    @ResponseBody
    @ApiOperation("批量插入员工接口")
    public CommonResult<String> insertBatch(@RequestBody List<Employee> employees) {
        int result = employeeService.insertBatch(employees);
        return result>0?new CommonResult<>(200,"批量插入成功 ","插入记录: "+result):new CommonResult<>(200,"批量插入失败");
    }
}
