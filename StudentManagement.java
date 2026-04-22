import java.sql.*;
import java.util.Scanner;

public class StudentManagement {

    static final String URL = "jdbc:mysql://localhost:3306/student_db";
    static final String USER = "root";
    static final String PASS = "Root@123"; // change this

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== Student Registration System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static Connection connect() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    static void addStudent() {
        try {
            Connection con = connect();

            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            String query = "INSERT INTO students VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, course);

            ps.executeUpdate();
            System.out.println("Student Added!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void viewStudents() {
        try {
            Connection con = connect();

            String query = "SELECT * FROM students";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\nID | Name | Age | Course");

            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | " +
                        rs.getString(2) + " | " +
                        rs.getInt(3) + " | " +
                        rs.getString(4)
                );
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void updateStudent() {
        try {
            Connection con = connect();

            System.out.print("Enter ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new course: ");
            String course = sc.nextLine();

            String query = "UPDATE students SET course=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, course);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Updated!");
            else
                System.out.println("Student not found!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deleteStudent() {
        try {
            Connection con = connect();
System.out.print("Enter ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Deleted!");
            else
                System.out.println("Student not found!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

