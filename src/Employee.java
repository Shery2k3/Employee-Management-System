public class Employee {
    private String name;
    private int id;
    private String position;
    private double salary;

    public Employee(String name, int id, double salary, String position) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.position = position;
    }

    public void printEmployee() {
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
        System.out.println("Salary: " + salary);
        System.out.println("Position: " + position);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }
}