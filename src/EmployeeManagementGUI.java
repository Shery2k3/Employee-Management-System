import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.event.*;

public class EmployeeManagementGUI {

    private JFrame frame;
    private JTextField[] textFields;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private ButtonGroup searchGroup;
    private EmployeeManager employeeManager;
    private JButton undoButton;

    public EmployeeManagementGUI() {
        employeeManager = new EmployeeManager();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Employee Management System");
        frame.setBounds(100, 100, 1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(240, 240, 240));
        frame.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));

        JPanel inputPanel = createInputPanel();
        inputPanel.setPreferredSize(new Dimension(300, frame.getHeight()));
        JPanel outputPanel = createOutputPanel();

        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        frame.add(createHeaderPanel(), BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        updateUndoButtonState();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("Lora", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Employee Information"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        inputPanel.setBackground(new Color(236, 240, 241));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        String[] labels = {"ID", "Name", "Role", "Contact", "Email", "Salary"};
        textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.LINE_START;
            inputPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_END;
            textFields[i] = new JTextField(25);
            inputPanel.add(textFields[i], gbc);
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(new Color(236, 240, 241));
        JButton insertButton = createStyledButton("Insert", this::insertEmployee);
        JButton updateButton = createStyledButton("Update", this::updateEmployee);
        insertButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setPreferredSize(new Dimension(100, 30));
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5);
        inputPanel.add(buttonPanel, gbc);

        // Add empty space at the bottom to center content vertically
        gbc.gridy = labels.length + 1;
        gbc.weighty = 1.0;
        inputPanel.add(Box.createVerticalGlue(), gbc);

        return inputPanel;
    }

    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout(10, 10));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Employee Records"));
        outputPanel.setBackground(new Color(236, 240, 241));

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Name", "Role", "Contact", "Salary", "Email"}, 0);
        employeeTable = new JTable(tableModel);
        employeeTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBackground(new Color(236, 240, 241));
        controlPanel.add(createStyledButton("Load Data", e -> loadData()));
        controlPanel.add(createStyledButton("Delete", this::deleteEmployee));
        searchField = new JTextField(15);
        controlPanel.add(searchField);
        controlPanel.add(createStyledButton("Search", this::searchEmployee));
        undoButton = createStyledButton("Undo", this::undoOperation);
        undoButton.setEnabled(false);
        controlPanel.add(undoButton);

        searchGroup = new ButtonGroup();
        String[] searchOptions = {"Name", "ID", "Email"};
        for (String option : searchOptions) {
            JRadioButton radio = new JRadioButton(option);
            radio.setBackground(new Color(236, 240, 241));
            searchGroup.add(radio);
            controlPanel.add(radio);
        }

        outputPanel.add(controlPanel, BorderLayout.NORTH);

        return outputPanel;
    }

    private void undoOperation(ActionEvent e) {
        try {
            employeeManager.undo();
            loadData();
            JOptionPane.showMessageDialog(frame, "Operation undone successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateUndoButtonState();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error undoing operation", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateUndoButtonState() {
        undoButton.setEnabled(employeeManager.canUndo());
    }


    private JPanel createLabeledField(String label, int index) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setBackground(new Color(236, 240, 241));
        JLabel jLabel = new JLabel(label);
        textFields[index] = new JTextField(20);
        panel.add(jLabel, BorderLayout.WEST);
        panel.add(textFields[index], BorderLayout.CENTER);
        return panel;
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void insertEmployee(ActionEvent e) {
        if (areAllFieldsFilled()) {
            try {
                EmployeeData employee = new EmployeeData(
                        textFields[0].getText(), textFields[1].getText(), textFields[2].getText(),
                        textFields[3].getText(), textFields[5].getText(), textFields[4].getText()
                );
                employeeManager.insertEmployee(employee);
                loadData();
                updateUndoButtonState();
                JOptionPane.showMessageDialog(frame, "Employee successfully inserted", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error inserting employee", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee(ActionEvent e) {
        if (isIdFieldFilled() && isAtLeastOneOtherFieldFilled()) {
            try {
                EmployeeData employee = new EmployeeData(
                        textFields[0].getText(), textFields[1].getText(), textFields[2].getText(),
                        textFields[3].getText(), textFields[5].getText(), textFields[4].getText()
                );
                employeeManager.updateEmployee(employee);
                loadData();
                updateUndoButtonState();
                JOptionPane.showMessageDialog(frame, "Employee successfully updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error updating employee", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill the ID field and at least one other field", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean areAllFieldsFilled() {
        for (JTextField field : textFields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isIdFieldFilled() {
        return !textFields[0].getText().isEmpty();
    }

    private boolean isAtLeastOneOtherFieldFilled() {
        for (int i = 1; i < textFields.length; i++) {
            if (!textFields[i].getText().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void deleteEmployee(ActionEvent e) {
        try {
            employeeManager.deleteEmployee(searchField.getText());
            loadData();
            updateUndoButtonState();
            JOptionPane.showMessageDialog(frame, "Employee successfully deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error deleting employee", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void searchEmployee(ActionEvent e) {
        try {
            String searchType = searchGroup.getSelection() != null ?
                    ((JRadioButton) searchGroup.getSelection()).getText() : "";
            switch (searchType) {
                case "Name":
                    employeeManager.searchEmployee(tableModel, searchField.getText(), "Employee_name");
                    break;
                case "ID":
                    employeeManager.searchEmployee(tableModel, searchField.getText(), "Employee_id");
                    break;
                case "Email":
                    employeeManager.searchEmployee(tableModel, searchField.getText(), "Employee_email");
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Please select a search field", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Employee not found", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void loadData() {
        try {
            employeeManager.loadData(tableModel);
            updateUndoButtonState();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public JFrame getFrame() {
        return frame;
    }
}