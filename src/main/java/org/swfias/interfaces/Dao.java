package org.swfias.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> getById(long userId) throws SQLException;

    List<T> getAll() throws SQLException;

    void save(T t) throws SQLException;

    void clearAll() throws SQLException;

    void update(T t, String[] params);

    void delete(T t) throws SQLException;
}
