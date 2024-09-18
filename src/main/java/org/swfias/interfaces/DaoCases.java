package org.swfias.interfaces;


import org.swfias.dtos.CaseDto;

import java.sql.SQLException;
import java.util.List;

public interface DaoCases<T> {
    List<T> getAllById(long userId) throws SQLException;

    List<T> getAllByAssignToId(long assignedTo) throws SQLException;

    CaseDto getByTitle(String title) throws SQLException;

    boolean isTitleDuplicate(String title) throws SQLException;


}
