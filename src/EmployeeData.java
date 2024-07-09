public class EmployeeData {
    private String id;
    private String name;
    private String department;
    private String contact;
    private String salary;
    private String email;

    public EmployeeData(String id, String name, String department,
                        String contact, String salary, String email) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.contact = contact;
        this.salary = salary;
        this.email = email;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Object[] toArray() {
        return new Object[]{id, name, department, contact, salary, email};
    }
}