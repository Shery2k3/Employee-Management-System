public class EmployeeData {
    private String id;
    private String name;
    private String department;
    private String joinDate;
    private String gender;
    private String contact;
    private String salary;
    private String email;
    private String address;

    public EmployeeData(String id, String name, String department, String joinDate, String gender,
                        String contact, String salary, String email, String address) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.joinDate = joinDate;
        this.gender = gender;
        this.contact = contact;
        this.salary = salary;
        this.email = email;
        this.address = address;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Object[] toArray() {
        return new Object[]{id, name, department, joinDate, gender, contact, salary, email, address};
    }
}