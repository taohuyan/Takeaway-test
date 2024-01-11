package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO
     */
    void insertEmp(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult QueryByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用或禁用员工账号
     * @param status
     */
    void OpenOrDown(Integer status,Long id);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    Employee QueryById(Long id);

    /**
     * 修改员工信息
     * @param employeeDTO
     */
    void Update(EmployeeDTO employeeDTO);

    /**
     * 修改密码
     * @param passwordEditDTO
     */
    void editPassword(PasswordEditDTO passwordEditDTO);
}
