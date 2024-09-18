package org.swfias.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static Database db;
    public Connection connection;

    protected static final Logger logger = LogManager.getLogger(Database.class);

    private Database() {

    }

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
            logger.log(Level.INFO, "New database object  has been initialized.");
        }
        return db;
    }

    private static Connection resolveConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/csm", "root", "123456");//Change your database connection
        if (conn != null) {
            logger.log(Level.INFO, "Connected to the database!");
            return conn;
        } else {
            logger.log(Level.ERROR, "Failed to make connection!");
            return null;
        }
    }

    public Connection getConnection() throws SQLException {
        this.connection = resolveConnection();
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (!this.connection.isClosed()) {
            this.connection.close();
            logger.log(Level.INFO, "Database connection has been closed successfully!");
        }
    }
}
