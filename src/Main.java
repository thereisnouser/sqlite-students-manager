import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        prepareDatabase();

        int operationNumber = 0;

        do {
            System.out.println("\n\n--> Supported operations: <--\n");
            System.out.println("1: Create a student");
            System.out.println("2: Delete a student");
            System.out.println("3: List all students");
            System.out.println("4: Exit");
            System.out.print("\nEnter a number of operation you want to execute: ");

            final String input = scanner.nextLine();

            try {
                operationNumber = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n!!! Your number is invalid !!!");
                continue;
            }

            switch (operationNumber) {
                case 1:
                    createStudent();
                    break;

                case 2:
                    deleteStudentById();
                    break;

                case 3:
                    selectStudentsList();
                    break;

                case 4:
                    closeConnection();
                    break;

                default:
                    System.out.println("\nThere is no such number in the list...");
                    break;
            }
        } while (operationNumber != 4);
    }

    public static void prepareDatabase() {
        openConnection();
        createStudentsTable();
    }

    public static void openConnection() {
        final String pathToDatabase = Paths.get("db/students.db").toAbsolutePath().toString();
        final String urlToDatabaseConnect = "jdbc:sqlite:" + pathToDatabase;

        try {
            connection = DriverManager.getConnection(urlToDatabaseConnect);
            System.out.println("\n--> You are connected to the 'students' database! <--");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createStudentsTable() {
        final String query = "CREATE TABLE IF NOT EXISTS students (\n"
            + "id         INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "name       TEXT,\n"
            + "surname    TEXT,\n"
            + "patronymic TEXT,\n"
            + "birthday   TEXT,\n"
            + "group_id   TEXT\n"
            + ");";

        executeQuery(query);
    }

    public static void createStudent() {
        String name;
        String surname;
        String patronymic;
        String birthday;
        String groupId;

        System.out.print("Enter name: ");
        name = scanner.nextLine();
        System.out.print("Enter surname: ");
        surname = scanner.nextLine();
        System.out.print("Enter patronymic: ");
        patronymic = scanner.nextLine();
        System.out.print("Enter birthday: ");
        birthday = scanner.nextLine();
        System.out.print("Enter group: ");
        groupId = scanner.nextLine();

        final String query = String.format(
            "INSERT INTO students (name, surname, patronymic, birthday, group_id)"
            + " VALUES ('%s', '%s', '%s', '%s', '%s');", name, surname, patronymic, birthday, groupId
        );

        executeQuery(query);
    }

    public static void deleteStudentById() {
        System.out.print("Enter an 'id' of a student you want to delete: ");

        int id = 0;
        final String input = scanner.nextLine();

        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("\n!!! The 'id' number is invalid !!!");
            return;
        }

        final String query = "DELETE FROM students WHERE id = " + id + ";";

        executeQuery(query);
    }

    public static void selectStudentsList() {
        try (final Statement statement = connection.createStatement()) {
            final String query = "SELECT * FROM students;";
            final ResultSet result = statement.executeQuery(query);

            System.out.println("\n--- BEGIN ---\n");
            while (result.next()) {
                final int id = result.getInt("id");
                final String name = result.getString("name");
                final String surname = result.getString("surname");
                final String patronymic = result.getString("patronymic");
                final String birthday = result.getString("birthday");
                final String groupId = result.getString("group_id");

                System.out.println(String.format(
                    "%d | %s | %s | %s | %s | %s",
                    id, name, surname, patronymic, birthday, groupId)
                );
            }
            System.out.println("\n--- END ---");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void executeQuery(final String query) {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("\n--> Connection to the 'students' database is closed! <--\n");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
