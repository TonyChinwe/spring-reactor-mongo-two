package com.phisoft.springreactormongoone.services;

import com.phisoft.springreactormongoone.dto.EmployeeDto;
import com.phisoft.springreactormongoone.repositories.EmployeeRepository;
import com.phisoft.springreactormongoone.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;


/**
 * Service implementation  for doing the actual CRUD operation in our mongodb database
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    /**
     * Fetches all the saved Employee in mongodb database
     * @return Flux<EmployeeDto></EmployeeDto>
     */
    public Flux<EmployeeDto> getEmployees(){
        return employeeRepository.findAll().map(AppUtils::toDto);
    }

    /**
     * Streams all the saved Employee in mongodb database
     * @return them as Flux<EmployeeDto></EmployeeDto>
     */
    public Flux<EmployeeDto> streamEmployees(){
     return  employeeRepository.findAll().map(AppUtils::toDto);
    }

    /**
     * Fetches an Employee using employee id
     * @param id employee id
     * @return Mono<EmployeeDto></EmployeeDto>
     */
    public Mono<EmployeeDto> getEmployee(String id){
        return employeeRepository.findById(id).map(AppUtils::toDto);
    }

    /**
     * Fetches list of Employee using employee based on salary range
     * @param min minimum salary range
     * @param max maximum salary range
     * @return Flux<EmployeeDto></EmployeeDto>
     */
    public Flux<EmployeeDto> getSalaryInRange(Long min,Long max){
        return employeeRepository.findBySalaryBetween(min,max).map(AppUtils::toDto);
    }

    /**
     * Saves an Employee to our mongodb database
     * @param employeeDto Mono of employee dto to be saved
     * @return Mono<EmployeeDto></EmployeeDto>
     */
    public Mono<EmployeeDto> saveEmployee(Mono<EmployeeDto> employeeDto){
     return employeeDto.map(AppUtils::toEntity)
                .flatMap(employeeRepository::insert)
                .map(AppUtils::toDto);
    }
    /**
     * Update an already saved Employee in our mongodb database using the Employee id
     * @param employeeDto Mono of employee dto to be saved
     * @param id id of the Employee to be updated
     * @return Mono<EmployeeDto></EmployeeDto>
     */
    public Mono<EmployeeDto> updateEmployee(Mono<EmployeeDto> employeeDto,String id){
        return employeeRepository.findById(id)
                .flatMap(p->employeeDto.map(AppUtils::toEntity))
                .doOnNext(e->e.setId(id))
                .flatMap(employeeRepository::save)
                .map(AppUtils::toDto);
    }
    /**
     * Delete already saved Employee from our mongodb database using the Employee id
     * @param id id of the Employee to be deleted
     * @return Mono<Void></EmployeeDto>
     */
    public Mono<Void> deleteEmployee(String id){
        return employeeRepository.deleteById(id);
    }
}
