import java.sql.*;

public class Doctors {

    Connection con;

    Doctors(Connection con) {
        this.con = con;
    }

    public void viewDoctors() {

        String query = "SELECT * FROM doctors";

        try {
            PreparedStatement pr = con.prepareStatement(query);
            ResultSet set = pr.executeQuery();

            System.out.println("\nDoctors:");
            System.out.println("+------------+----------------------+----------------------+");
            System.out.println("| Doctor ID  |      Doctor Name     |     Specialization   |");
            System.out.println("+------------+----------------------+----------------------+");

            while (set.next()) {

                int id = set.getInt("doctor_id");
                String name = set.getString("name");
                String specialization = set.getString("specialization");

                System.out.printf("| %-10d | %-20s | %-20s |\n", id, name, specialization);
            }

            System.out.println("+------------+----------------------+----------------------+");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {

        String query = "SELECT * FROM doctors WHERE doctor_id = ?";

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