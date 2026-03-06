import java.sql.*;
import java.util.*;

public class hospitalmanagement {
    static String url = System.getenv("URL");
static String username = System.getenv("USERNAME");
static String password = System.getenv("PASSWORD");
;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connection Successful");

            Patient patient = new Patient(con, sc);
            Doctors doctor = new Doctors(con);

            while (true) {

                System.out.println("\n===== Hospital Management System =====");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");

                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        patient.addPatient();
                        break;

                    case 2:
                        patient.viewPatient();
                        break;

                    case 3:
                        doctor.viewDoctors();
                        break;

                    case 4:
                        bookAppointment(patient, doctor, con, sc);
                        break;

                    case 5:
                        System.out.println("Closing connection...");
                        con.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection");
            }
        }
    }

    // ================= BOOK APPOINTMENT =================

    public static void bookAppointment(Patient patient, Doctors doctor, Connection con, Scanner sc) {

        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();

        System.out.print("Enter Doctor ID: ");
        int doctorId = sc.nextInt();

        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
        String appointmentDate = sc.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {

            if (checkDoctorAvailability(doctorId, appointmentDate, con)) {

                String query = "INSERT INTO appointments (doctor_id, patient_id, appointment_date) VALUES (?, ?, ?)";

                try {

                    PreparedStatement ps = con.prepareStatement(query);

                    ps.setInt(1, doctorId);
                    ps.setInt(2, patientId);
                    ps.setString(3, appointmentDate);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        System.out.println("Appointment booked successfully!");
                    } else {
                        System.out.println("Appointment booking failed.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        } else {
            System.out.println("Invalid Doctor ID or Patient ID.");
        }
    }

    // ================= CHECK DOCTOR AVAILABILITY =================

    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {

        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setInt(1, doctorId);
            ps.setString(2, appointmentDate);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int count = rs.getInt(1);

                if (count == 0) {
                    return true;
                } else {
                    System.out.println("Doctor is not available on this date!");
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}