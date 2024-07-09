public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                Database.dbInit();
                EmployeeManagementGUI gui = new EmployeeManagementGUI();
                gui.getFrame().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}