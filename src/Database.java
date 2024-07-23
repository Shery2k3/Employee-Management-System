import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.sqlite.SQLiteDataSource;

public class Database {
    static Connection conn;
    static SQLiteDataSource ds;

    public static void dbInit() {
        ds = new SQLiteDataSource();

        try {
            ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:EmployeeDB.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.exit(0);
        }
        try {
            conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS EmployeeData ( "
                    + "Employee_id TEXT PRIMARY KEY ,"
                    + "Employee_name TEXT,"
                    + "department TEXT,"
                    + "Employee_contact TEXT,"
                    + "Employee_email TEXT,"
                    + "salary TEXT"
                    + " );";
            stmt.executeUpdate(query);
            conn.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
    }

    protected static void insertEmployeeData(String id, String name, String department,
                                             String contact, String salary, String email
    ) throws SQLException {
        String query = "INSERT INTO EmployeeData("
                + "Employee_id,"
                + "Employee_name,"
                + "department,"
                + "Employee_contact,"
                + "salary,"
                + "Employee_email) "
                + "VALUES("
                +"'"+ id +"',"
                +"'"+ name +"',"
                +"'"+ department +"',"
                +"'"+ contact +"',"
                +"'"+ salary +"',"
                +"'"+ email +"');" ;

        conn = ds.getConnection();
        Statement stmt =  conn.createStatement();
        stmt.executeUpdate(query);
        conn.close();
    }

    protected static void updateEmployeeData(String id, String name, String department,
                                             String contact, String salary, String email
    ) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE EmployeeData SET ");

        if (!name.isEmpty()) {
            query.append("Employee_name = '").append(name).append("', ");
        }
        if (!department.isEmpty()) {
            query.append("department = '").append(department).append("', ");
        }
        if (!contact.isEmpty()) {
            query.append("Employee_contact = '").append(contact).append("', ");
        }
        if (!salary.isEmpty()) {
            query.append("salary = '").append(salary).append("', ");
        }
        if (!email.isEmpty()) {
            query.append("Employee_email = '").append(email).append("', ");
        }

        // Remove the last comma and space
        query.setLength(query.length() - 2);

        query.append(" WHERE Employee_id = '").append(id).append("'");

        conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query.toString());
        conn.close();
    }

    protected static void deleteEmployeeData(String id) throws SQLException {
        String query = "DELETE FROM EmployeeData WHERE Employee_id = '"+id+"';";
        conn = ds.getConnection();
        Statement stmt =  conn.createStatement();
        stmt.executeUpdate(query);
        conn.close();
    }

    public static void searchEmployeeData(DefaultTableModel model,String searchTerm,String column) throws SQLException {
        model.setRowCount(0);
        String query = "SELECT * FROM EmployeeData WHERE "+column+" LIKE '%"+searchTerm +"%';";
        conn = ds.getConnection();
        Statement stmt =  conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
            String id = rs.getString("Employee_id");
            String name = rs.getString("Employee_name");
            String department = rs.getString("department");
            String contact = rs.getString("Employee_contact");
            String salary = rs.getString("salary");
            String email = rs.getString("Employee_email");

            model.addRow(new Object[]{id,name,department,contact,salary,email});
        }
        conn.close();
        rs.close();
    }

    public static void loadData(DefaultTableModel model) throws SQLException {
        model.setRowCount(0);
        String query = "SELECT * FROM EmployeeData ;";
        conn = ds.getConnection();
        Statement stmt =  conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next()) {
            String id = rs.getString("Employee_id");
            String name = rs.getString("Employee_name");
            String department = rs.getString("department");
            String contact = rs.getString("Employee_contact");
            String salary = rs.getString("salary");
            String email = rs.getString("Employee_email");

            model.addRow(new Object[]{id,name,department,contact,salary,email});
        }
        conn.close();
        rs.close();
    }
}