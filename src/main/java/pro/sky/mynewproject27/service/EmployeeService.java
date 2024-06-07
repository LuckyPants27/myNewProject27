package pro.sky.mynewproject27.service;

import org.springframework.stereotype.Service;
import pro.sky.mynewproject27.exception.EmployeeAlreadyAddedException;
import pro.sky.mynewproject27.exception.EmployeeNotFoundException;
import pro.sky.mynewproject27.exception.EmployeeStorageIsFullException;
import pro.sky.mynewproject27.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final int maxEmployees = 10;
    private final Map<String, Employee> employees = new HashMap<>();

    public Employee add(String firstName, String lastName) {
        String key = buildKey(firstName, lastName);
        if (employees.containsKey(key)) {
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size() >= maxEmployees) {
            throw new EmployeeStorageIsFullException();
        }
        Employee employee = new Employee(firstName, lastName);
        employees.put(key, employee);
        return employee;
    }

    public Employee remove(String firstName, String lastName) {
        String key = buildKey(firstName, lastName);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.remove(key);
    }

    public Employee find(String firstName, String lastName) {
        String key = buildKey(firstName, lastName);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);
    }

    private String buildKey(String firstName, String lastName) {
        return firstName + lastName;
    }
    public List<Employee> findAll() {
        return List.copyOf(employees.values());
    }
}
