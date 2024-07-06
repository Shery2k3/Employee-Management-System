import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeManager {
    private List<Employee> employees;
    private Map<Integer, Employee> employeeMap;

    public EmployeeManager() {
        employees = new ArrayList<>();
        employeeMap = new HashMap<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employeeMap.put(employee.getId(), employee);
    }

    public Employee getEmployee(int id) {
        return employeeMap.get(id);
    }

    public void updateEmployee(int id, Employee updatedEmployee) {
        int index = employees.indexOf(getEmployee(id));
        if (index != -1) {
            employees.set(index, updatedEmployee);
            employeeMap.put(id, updatedEmployee);
        }
    }

    public void removeEmployee(int id) {
        Employee employee = getEmployee(id);
        employees.remove(employee);
        employeeMap.remove(id);
    }

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }
}