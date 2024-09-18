package org.swfias.interfaces;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swfias.daos.PersonDao;
import org.swfias.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DaoBase<T> implements Dao<T> {

    protected Connection connection;
    protected static final String QUERY_CLEARALL_CASES = "DELETE FROM cases";
    protected static final String QUERY_CLEARALL_PERSONS = "DELETE FROM persons";
    private static final Logger logger = LogManager.getLogger(DaoBase.class);

    {
        try {
            connection = Database.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearAll() throws SQLException {
        try (PreparedStatement psFaultReports = this.connection.prepareStatement(QUERY_CLEARALL_CASES);
             PreparedStatement psPerson = this.connection.prepareStatement(QUERY_CLEARALL_PERSONS);
             PreparedStatement psPersonResetAutoIncrement = this.connection.prepareStatement("ALTER TABLE persons AUTO_INCREMENT = 1");
             PreparedStatement psFaultReportsResetAutoIncrement = this.connection.prepareStatement("ALTER TABLE cases AUTO_INCREMENT = 1");
        ) {
            psFaultReports.executeUpdate();
            psPerson.executeUpdate();
            psPersonResetAutoIncrement.executeUpdate();
            psFaultReportsResetAutoIncrement.executeUpdate();
            logger.log(Level.INFO, "Cleared tables cases and persons.");
        }
    }
}
