import java.sql.SQLException;
import java.util.HashMap;
import java.util.Deque;
import java.util.ArrayDeque;
import javax.swing.table.DefaultTableModel;

public class EmployeeManager {
    private HashMap<String, EmployeeData> employeeCache;
    private Deque<UndoableOperation> undoStack;

    public EmployeeManager() {
        employeeCache = new HashMap<>();
        undoStack = new ArrayDeque<>();
    }

    public void insertEmployee(EmployeeData employee) throws SQLException {
        Database.insertEmployeeData(
                employee.getId(), employee.getName(), employee.getDepartment(),
                employee.getContact(), employee.getSalary(), employee.getEmail()
        );
        employeeCache.put(employee.getId(), employee);
        undoStack.push(new UndoableOperation(OperationType.INSERT, employee));
    }

    public void updateEmployee(EmployeeData newEmployee) throws SQLException {
        EmployeeData oldEmployee = employeeCache.get(newEmployee.getId());
        if (oldEmployee == null) {
            throw new IllegalArgumentException("No employee with ID " + newEmployee.getId());
        }

        EmployeeData updatedEmployee = new EmployeeData(
                oldEmployee.getId(),
                newEmployee.getName().isEmpty() ? oldEmployee.getName() : newEmployee.getName(),
                newEmployee.getDepartment().isEmpty() ? oldEmployee.getDepartment() : newEmployee.getDepartment(),
                newEmployee.getContact().isEmpty() ? oldEmployee.getContact() : newEmployee.getContact(),
                newEmployee.getSalary().isEmpty() ? oldEmployee.getSalary() : newEmployee.getSalary(),
                newEmployee.getEmail().isEmpty() ? oldEmployee.getEmail() : newEmployee.getEmail()
        );

        Database.updateEmployeeData(updatedEmployee.getId(), updatedEmployee.getName(), updatedEmployee.getDepartment(),
                updatedEmployee.getContact(), updatedEmployee.getSalary(), updatedEmployee.getEmail());
        employeeCache.put(updatedEmployee.getId(), updatedEmployee);
        undoStack.push(new UndoableOperation(OperationType.UPDATE, oldEmployee));
    }

    public void deleteEmployee(String id) throws SQLException {
        EmployeeData deletedEmployee = new EmployeeData(
                employeeCache.get(id).getId(),
                employeeCache.get(id).getName(),
                employeeCache.get(id).getDepartment(),
                employeeCache.get(id).getContact(),
                employeeCache.get(id).getSalary(),
                employeeCache.get(id).getEmail()
        );
        Database.deleteEmployeeData(id);
        employeeCache.remove(id);
        undoStack.push(new UndoableOperation(OperationType.DELETE, deletedEmployee));
    }

    public void searchEmployee(DefaultTableModel model, String searchTerm, String column) throws SQLException {
        Database.searchEmployeeData(model, searchTerm, column);
    }

    public void loadData(DefaultTableModel model) throws SQLException {
        Database.loadData(model);
        employeeCache.clear();
        for (int i = 0; i < model.getRowCount(); i++) {
            EmployeeData employee = new EmployeeData(
                    (String) model.getValueAt(i, 0), (String) model.getValueAt(i, 1),
                    (String) model.getValueAt(i, 2), (String) model.getValueAt(i, 3),
                    (String) model.getValueAt(i, 4), (String) model.getValueAt(i, 5)
            );
            employeeCache.put(employee.getId(), employee);
        }
    }


    public void undo() throws SQLException {
        if (undoStack.isEmpty()) {
            throw new IllegalStateException("No operations to undo");
        }
        UndoableOperation lastOperation = undoStack.pop();
        switch (lastOperation.getType()) {
            case INSERT:
                Database.deleteEmployeeData(lastOperation.getEmployee().getId());
                employeeCache.remove(lastOperation.getEmployee().getId());
                break;
            case UPDATE:
                EmployeeData oldEmployee = lastOperation.getEmployee();
                Database.updateEmployeeData(
                        oldEmployee.getId(), oldEmployee.getName(), oldEmployee.getDepartment(),
                        oldEmployee.getContact(), oldEmployee.getEmail(), oldEmployee.getSalary()
                );
                employeeCache.put(oldEmployee.getId(), oldEmployee);
                break;
            case DELETE:
                EmployeeData deletedEmployee = lastOperation.getEmployee();
                Database.insertEmployeeData(
                        deletedEmployee.getId(), deletedEmployee.getName(), deletedEmployee.getDepartment(),
                        deletedEmployee.getContact(), deletedEmployee.getSalary(), deletedEmployee.getEmail()
                );
                employeeCache.put(deletedEmployee.getId(), deletedEmployee);
                break;
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

}

enum OperationType { INSERT, UPDATE, DELETE }

class UndoableOperation {
    private OperationType type;
    private EmployeeData employee;

    public UndoableOperation(OperationType type, EmployeeData employee) {
        this.type = type;
        this.employee = employee;
    }

    public OperationType getType() { return type; }
    public EmployeeData getEmployee() { return employee; }
}