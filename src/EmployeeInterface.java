import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeInterface extends JFrame {
    private EmployeeManager system;
    private JTextField nameField, idField, positionField, salaryField;
    private JButton addButton, removeButton, updateButton, listButton;

    public EmployeeInterface() {
        system = new EmployeeManager();

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        nameField = new JTextField(10);
        idField = new JTextField(10);
        positionField = new JTextField(10);
        salaryField = new JTextField(10);

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        updateButton = new JButton("Update");
        listButton = new JButton("List All");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                String position = positionField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                Employee employee = new Employee(name, id, salary, position);
                system.addEmployee(employee);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                system.removeEmployee(id);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int id = Integer.parseInt(idField.getText());
                String position = positionField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                Employee updatedEmployee = new Employee(name, id, salary, position);
                system.updateEmployee(id, updatedEmployee);
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Employee> allEmployees = system.getAllEmployees();
                for (Employee employee : allEmployees) {
                    System.out.println(employee);
                }
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Name:"), constraints);
        constraints.gridx = 1;
        add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("ID:"), constraints);
        constraints.gridx = 1;
        add(idField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Position:"), constraints);
        constraints.gridx = 1;
        add(positionField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("Salary:"), constraints);
        constraints.gridx = 1;
        add(salaryField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        add(addButton, constraints);
        constraints.gridx = 1;
        add(removeButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        add(updateButton, constraints);
        constraints.gridx = 1;
        add(listButton, constraints);

        pack();
        setLocationRelativeTo(null); // This will center the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}