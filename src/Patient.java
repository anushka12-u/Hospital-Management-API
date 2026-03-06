import java.sql.*;
import java.util.*;

public class Patient {

    Connection con;
    Scanner scanner;

    Patient(Connection con, Scanner scanner) {
        this.con = con;
        this.scanner = scanner;
    }

    public void addPatient() {

        System.out.print("Enter Patient Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Enter numeric age.");
            scanner.next();
        }

        int age = scanner.nextInt();

        System.out.print("Enter Gender: ");
        String gender = scanner.next();

        try {

            String query = "INSERT INTO patient (name, age, gender) VALUES (?, ?, ?)";

            PreparedStatement pr = con.prepareStatement(query);

            pr.setString(1, name);
            pr.setInt(2, age);
            pr.setString(3, gender);

            int rows = pr.executeUpdate();

            if (rows > 0) {
                System.out.println("Patient added successfully!");
            } else {
                System.out.println("Failed to add patient.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {

        String query = "SELECT * FROM patient";

        try {

            PreparedStatement pr = con.prepareStatement(query);
            ResultSet set = pr.executeQuery();

            System.out.println("\nPatients:");
            System.out.println("+------------+----------------------+-------+---------+");
            System.out.println("| Patient ID |     Patient Name     |  Age  | Gender  |");
            System.out.println("+------------+----------------------+-------+---------+");

            while (set.next()) {

                int id = set.getInt("patient_id");
                String name = set.getString("name");
                int age = set.getInt("age");
                String gender = set.getString("gender");

                System.out.printf("| %-10d | %-20s | %-5d | %-7s |\n",
                        id, name, age, gender);
            }

            System.out.println("+------------+----------------------+-------+---------+");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {

        String query = "SELECT * FROM patient WHERE patient_id = ?";

        try {

            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);

            ResultSet set = pr.executeQuery();

            return set.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}