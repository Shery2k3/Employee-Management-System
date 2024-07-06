import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeInterface extends JFrame {
    private EmployeeManager system;
    private JTextField nameField, idField, positionField, salaryField;
    private JButton addButton, removeButton, updateButton;

    public EmployeeInterface() {
        system = new EmployeeManager();

        setLayout(new FlowLayout());

        nameField = new JTextField("Name");
        idField = new JTextField("ID");
        positionField = new JTextField("Position");
        salaryField = new JTextField("Salary");

        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        updateButton = new JButton("Update");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add employee
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove employee
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update employee
            }
        });

        add(nameField);
        add(idField);
        add(positionField);
        add(salaryField);
        add(addButton);
        add(removeButton);
        add(updateButton);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}