package com.thereisnouserwebsite.dao;

import com.thereisnouserwebsite.connection.ConnectionManager;
import com.thereisnouserwebsite.util.ColumnKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private static final StudentDao INSTANCE = new StudentDao();

    static {
        createStudentsTable();
    }

    private StudentDao() {
    }

    public List<String> getAllStudents() {
        final String sqlQuery = String.format(
                "SELECT %s, %s, %s, %s, %s, %s FROM students;",
                ColumnKey.ID_KEY,
                ColumnKey.NAME_KEY,
                ColumnKey.SURNAME_KEY,
                ColumnKey.PATRONYMIC_KEY,
                ColumnKey.AGE_KEY,
                ColumnKey.GROUP_ID_KEY
        );

        try (final Connection connection = ConnectionManager.open();
             final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            final ResultSet resultSet = preparedStatement.executeQuery();
            final List<String> studentsList = new ArrayList<>();

            while (resultSet.next()) {
                final String studentInfo = ""
                        + " | " + ColumnKey.ID_KEY + ": " + resultSet.getInt(ColumnKey.ID_KEY)
                        + " | " + ColumnKey.NAME_KEY + ": " + resultSet.getString(ColumnKey.NAME_KEY)
                        + " | " + ColumnKey.SURNAME_KEY + ": " + resultSet.getString(ColumnKey.SURNAME_KEY)
                        + " | " + ColumnKey.PATRONYMIC_KEY + ": " + resultSet.getString(ColumnKey.PATRONYMIC_KEY)
                        + " | " + ColumnKey.AGE_KEY + ": " + resultSet.getInt(ColumnKey.AGE_KEY)
                        + " | " + ColumnKey.GROUP_ID_KEY + ": " + resultSet.getInt(ColumnKey.GROUP_ID_KEY)
                        + " | ";

                studentsList.add(studentInfo);
            }

            return studentsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createStudent(final String name,
                              final String surname,
                              final String patronymic,
                              final int age,
                              final int groupId) {
        final String sqlQuery = String.format(
                "INSERT INTO students (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?);",
                ColumnKey.NAME_KEY,
                ColumnKey.SURNAME_KEY,
                ColumnKey.PATRONYMIC_KEY,
                ColumnKey.AGE_KEY,
                ColumnKey.GROUP_ID_KEY
        );

        try (final Connection connection = ConnectionManager.open();
             final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, patronymic);
            preparedStatement.setInt(4, age);
            preparedStatement.setInt(5, groupId);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStudentById(final int id) {
        final String sqlQuery = String.format(
                "DELETE FROM students WHERE %s = ?;",
                ColumnKey.ID_KEY
        );

        try (final Connection connection = ConnectionManager.open();
             final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static StudentDao getInstance() {
        return INSTANCE;
    }

    private static void createStudentsTable() {
        final String sqlQuery = String.format(
                "CREATE TABLE IF NOT EXISTS students ("
                        + "%s INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "%s TEXT, "
                        + "%s TEXT, "
                        + "%s TEXT, "
                        + "%s INTEGER, "
                        + "%s INTEGER);",
                ColumnKey.ID_KEY,
                ColumnKey.NAME_KEY,
                ColumnKey.SURNAME_KEY,
                ColumnKey.PATRONYMIC_KEY,
                ColumnKey.AGE_KEY,
                ColumnKey.GROUP_ID_KEY
        );

        try (final Connection connection = ConnectionManager.open();
             final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
