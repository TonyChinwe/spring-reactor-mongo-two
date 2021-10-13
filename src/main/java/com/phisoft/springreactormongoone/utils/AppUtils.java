package com.phisoft.springreactormongoone.utils;


import com.phisoft.springreactormongoone.dto.EmployeeDto;
import com.phisoft.springreactormongoone.models.Employee;
import org.springframework.beans.BeanUtils;

/**
 * Utility class that converts from Employee model to EmployeeDto and vice versa
 */
public class AppUtils {

    /**
     * Converts Employee dto to Employee model
     * @param employeeDto employee dto
     */
    public static Employee toEntity(EmployeeDto employeeDto){
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDto,employee);
        return employee;
    }
    /**
     * Converts Employee model  to Employee dto
     * @param employee employee model
     */
    public static EmployeeDto toDto(Employee employee){
        EmployeeDto employeeDto=new EmployeeDto();
        BeanUtils.copyProperties(employee,employeeDto);
        return employeeDto;
    }
}
