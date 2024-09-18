package org.swfias.interfaces;

import org.swfias.dtos.PersonDto;

import java.sql.SQLException;
import java.util.List;


public interface DaoPerson {

    public PersonDto LoginCheck(String userId, String password) throws SQLException;

    public List<PersonDto> getByType(String type) throws SQLException;
}
