package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);


    /**
     * 插入员工数据
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, " +
            "create_time, update_time, create_user, update_user) values " +
            "(#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, " +
            "#{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insertEmp(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> QueryByPage(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用或禁用员工账号
     * @param employee
     */
    void Update(Employee employee);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    @Select("select * from employee where id = #{id}")
    Employee QueryById(Integer id);

}
