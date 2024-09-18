package org.swfias.daos;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swfias.dtos.AdminDto;
import org.swfias.dtos.CustomerDto;
import org.swfias.dtos.PersonDto;
import org.swfias.dtos.StaffDto;
import org.swfias.enums.PersonType;
import org.swfias.interfaces.DaoBase;
import org.swfias.interfaces.DaoPerson;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class PersonDao extends DaoBase<PersonDto> implements DaoPerson {

    protected static final Logger logger = LogManager.getLogger(PersonDao.class);

    private static final String QUERY_GETALL = "select * from persons";
    private static final String QUERY_GETBYID = "select * from persons where id= ?";
    private static final String QUERY_CHECKLOGIN = "select * from csm.persons where BINARY userId=? and BINARY password = ?";
    private static final String QUERY_USERID = "SELECT COUNT(*) FROM csm.persons where BINARY userId=?";
    private static final String QUERY_INSERT = "INSERT INTO persons (type, firstName, lastName, userId, password, email, address, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String QUERY_TYPE = "select * from persons Where type= ?";

    @Override
    public Optional<PersonDto> getById(long userId) throws SQLException {
        PreparedStatement ps
                = this.connection.prepareStatement(QUERY_GETBYID);
        ps.setString(1, String.valueOf(userId));
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            PersonDto personDto = new PersonDto();
            personDto.setId(rs.getLong("id"));
            personDto.setType(PersonType.valueOf(rs.getString("type")));
            personDto.setFirstName(rs.getString("firstName"));
            personDto.setLastName(rs.getString("lastName"));
            personDto.setUserId(rs.getString("userId"));
            personDto.setPassword(rs.getString("password"));
            personDto.setEmail(rs.getString("email"));
            personDto.setAddress(rs.getString("address"));
            personDto.setPhoneNumber(rs.getString("phoneNumber"));
            return Optional.of(personDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<PersonDto> getAll() throws SQLException {
        PreparedStatement ps
                = this.connection.prepareStatement(QUERY_GETALL);
        ResultSet rs = ps.executeQuery();
        List<PersonDto> ls = new ArrayList<>();

        while (rs.next()) {
            PersonDto personDto = new PersonDto();
            personDto.setId(rs.getLong("id"));
            personDto.setType(PersonType.valueOf(rs.getString("type")));
            personDto.setFirstName(rs.getString("firstName"));
            personDto.setLastName(rs.getString("lastName"));
            personDto.setUserId(rs.getString("userId"));
            personDto.setPassword(rs.getString("password"));
            personDto.setEmail(rs.getString("email"));
            personDto.setAddress(rs.getString("address"));
            personDto.setPhoneNumber(rs.getString("phoneNumber"));
            ls.add(personDto);
        }
        logger.info(" Found persons information in total: " + ls.size());
        return ls;
    }

    private boolean isUserIdUnique(String userId) throws SQLException {
        PreparedStatement ps
                = this.connection.prepareStatement(QUERY_USERID);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) == 0;
        }
        return false;
    }

    private String generateUniqueUserId(PersonDto personDto) throws SQLException {
        Random RANDOM = new Random();
        String userId;
        do {
            userId = personDto.getUserId() + "_" + RANDOM.nextInt(500);
        } while (!isUserIdUnique(userId));

        return userId;
    }

    ;

    @Override
    public void save(PersonDto personDto) throws SQLException {
        PreparedStatement ps
                = this.connection.prepareStatement(QUERY_INSERT);

        ps.setString(1, personDto.getType().toString());
        ps.setString(2, personDto.getFirstName());
        ps.setString(3, personDto.getLastName());
        ps.setString(4, generateUniqueUserId(personDto));
        ps.setString(5, personDto.getPassword());
        ps.setString(6, personDto.getEmail());
        ps.setString(7, personDto.getAddress());
        ps.setString(8, personDto.getPhoneNumber());
        int n = ps.executeUpdate();
        if (n == 1) {
            logger.log(Level.INFO, "User added successfully.");
        } else {
            logger.log(Level.WARN, "Ops! Problem occurred.");
        }
    }

    @Override
    public void update(PersonDto personDto, String[] params) {

    }

    @Override
    public void delete(PersonDto personDto) throws SQLException {

    }

    @Override
    public PersonDto LoginCheck(String userId, String password) throws SQLException {
        PreparedStatement ps
                = this.connection.prepareStatement(QUERY_CHECKLOGIN);
        ps.setString(1, userId);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        PersonDto personDto = null;

        while (rs.next()) {
            if (PersonType.valueOf(rs.getString("type")) == PersonType.ADMIN) {
                personDto = new AdminDto();
            } else if (PersonType.valueOf(rs.getString("type")) == PersonType.STAFF) {
                personDto = new StaffDto();
            } else if (PersonType.valueOf(rs.getString("type")) == PersonType.CUSTOMER) {
                personDto = new CustomerDto();
            } else {
                return personDto;
            }

            personDto.setId(rs.getLong("id"));
            personDto.setType(PersonType.valueOf(rs.getString("type")));
            personDto.setFirstName(rs.getString("firstName"));
            personDto.setLastName(rs.getString("lastName"));
            personDto.setUserId(rs.getString("userId"));
            personDto.setPassword(rs.getString("password"));
            personDto.setEmail(rs.getString("email"));
            personDto.setAddress(rs.getString("address"));
            personDto.setPhoneNumber(rs.getString("phoneNumber"));
        }

        return personDto;
    }

    @Override
    public List<PersonDto> getByType(String type) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(QUERY_TYPE);
        ps.setString(1, type);
        List<PersonDto> persons = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            PersonDto personDto = new PersonDto();
            personDto.setId(rs.getLong("id"));
            personDto.setType(PersonType.valueOf(rs.getString("type")));
            personDto.setFirstName(rs.getString("firstName"));
            personDto.setLastName(rs.getString("lastName"));
            personDto.setUserId(rs.getString("userId"));
            personDto.setPassword(rs.getString("password"));
            personDto.setEmail(rs.getString("email"));
            personDto.setAddress(rs.getString("address"));
            personDto.setPhoneNumber(rs.getString("phoneNumber"));
            persons.add(personDto);
        }
        if (persons.isEmpty()) {
            return null;
        }
        return persons;
    }

}
