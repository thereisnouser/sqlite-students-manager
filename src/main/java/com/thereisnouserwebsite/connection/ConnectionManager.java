package com.thereisnouserwebsite.connection;

import com.thereisnouserwebsite.util.PropertiesUtil;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String JDBC_DRIVER_KEY = "database.driver";

    private ConnectionManager() {
    }

    public static Connection open() {
        final String pathToDatabase = Paths.get("db/students.db").toAbsolutePath().toString();

        try {
            return DriverManager.getConnection(PropertiesUtil.getValue(JDBC_DRIVER_KEY) + ":" + pathToDatabase);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
