package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class EmployeeController {
    @Autowired
    private final EmployeeRepository employeeRepository;
    EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @GetMapping("/employees")
    List<Employee> all(){
        return employeeRepository.findAll();
    }
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee){
        return employeeRepository.save(newEmployee);
    }
    @GetMapping("/employees/{id}")
    ResponseEntity<?> one(@PathVariable Long id){

        return new ResponseEntity<>(employeeRepository.findById(id), HttpStatus.OK) ;
    }
    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee,@PathVariable Long id){

        return employeeRepository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            employee.setRole(newEmployee.getRole());
            return employeeRepository.save(employee);
        })
                .orElseGet(()-> {
                   newEmployee.setId(id);
                   return employeeRepository.save(newEmployee);
                });
    }
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);
    }
}
