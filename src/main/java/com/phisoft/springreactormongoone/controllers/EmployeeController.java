package com.phisoft.springreactormongoone.controllers;

import com.phisoft.springreactormongoone.dto.EmployeeDto;
import com.phisoft.springreactormongoone.services.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/employee")
@Api(value="Employee CRUD", description="CRUD Operations showing the use of spring web flux and reactive mongo db")
public class EmployeeController {

    @Autowired
    private EmployeeService service;


    @GetMapping
    @ApiOperation(value = "Fetches all the stored employee in the the mongodb database ", response = EmployeeDto.class)
    public Flux<EmployeeDto> getEmployees(){
        return service.getEmployees();
    }

    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ApiOperation(value = "Streams all the stored employee in the the mongodb database ", response = EmployeeDto.class)
    public Flux<EmployeeDto> streamEmployees(){
        return service.streamEmployees().delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Fetches an Employee from mongodb database",notes = "Fetches an Employee from mongodb database using the id of the Employee", response = EmployeeDto.class)
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String id){
        return service.getEmployee(id);
    }

    @GetMapping("/salary-range")
    @ApiOperation(value = "Fetches Employees from mongodb database based on salary",notes = "Fetches Employees from mongodb database based on salary range", response = EmployeeDto.class)
    public Flux<EmployeeDto> getSalaryRange(@RequestParam("min") Long min,@RequestParam("max") Long max){
        return service.getSalaryInRange(min,max);
    }

    @PostMapping("/save")
    @ApiOperation(value = "Saves an Employee to our mongodb database",notes = "Saves an Employee to our mongodb database", response = EmployeeDto.class)
    public Mono<EmployeeDto> save(@RequestBody Mono<EmployeeDto> employeeDto){
     return service.saveEmployee(employeeDto);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Updates an Employee in our mongodb database",notes = "Updates an Employee in our mongodb database using the id of the Employee", response = EmployeeDto.class)
    public Mono<EmployeeDto> update(@RequestBody Mono<EmployeeDto> employeeDto,@PathVariable("id") String id){
        return service.updateEmployee(employeeDto,id);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete an Employee from our mongodb database",notes = "Delete an Employee from our mongodb database using the id the Employee", response = EmployeeDto.class)
    public Mono<Void> delete(@PathVariable("id") String id){
        return service.deleteEmployee(id);
    }
}
