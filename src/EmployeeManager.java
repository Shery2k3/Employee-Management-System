import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.table.DefaultTableModel;

public class EmployeeManager {
    private HashMap<String, EmployeeData> employeeCache;
    private Queue<String> recentOperations;

    public EmployeeManager() {
        employeeCache = new HashMap<>();
        recentOperations = new LinkedList<>();
    }

    public void insertEmployee(EmployeeData employee) throws SQLException {
        Database.insertEmployeeData(
                employee.getId(), employee.getName(), employee.getDepartment(),
                employee.getContact(), employee.getSalary(), employee.getEmail()
        );
        employeeCache.put(employee.getId(), employee);
        addRecentOperation("Inserted: " + employee.getId());
    }

    public void updateEmployee(EmployeeData employee) throws SQLException {
        Database.updateEmployeeData(
                employee.getId(), employee.getName(), employee.getDepartment(),
                employee.getContact(), employee.getEmail(), employee.getSalary()
        );
        employeeCache.put(employee.getId(), employee);
        addRecentOperation("Updated: " + employee.getId());
    }

    public void deleteEmployee(String id) throws SQLException {
        Database.deleteEmployeeData(id);
        employeeCache.remove(id);
        addRecentOperation("Deleted: " + id);
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

    private void addRecentOperation(String operation) {
        recentOperations.offer(operation);
        if (recentOperations.size() > 5) {
            recentOperations.poll();
        }
    }

    public String getRecentOperations() {
        return String.join("\n", recentOperations);
    }
}