package com.sunrise.clinic.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * I used Singleton so the whole project shares one MySQL setup.
 * Username and password are read from db.properties (not hard-coded here).
 */
public final class DBConnection {
    private static final DBConnection INSTANCE = new DBConnection();
    private final String url;
    private final String username;
    private final String password;

    private DBConnection() {
        Properties properties = new Properties();
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties was not found on the classpath.");
            }
            properties.load(in);
            this.url = properties.getProperty("db.url");
            this.username = properties.getProperty("db.username");
            this.password = properties.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static DBConnection getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        // New connection each time so we always see the latest rows from MySQL.
        return DriverManager.getConnection(url, username, password);
    }
}
