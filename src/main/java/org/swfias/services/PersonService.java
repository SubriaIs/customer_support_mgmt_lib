package org.swfias.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swfias.daos.PersonDao;
import org.swfias.dtos.AdminDto;
import org.swfias.dtos.CustomerDto;
import org.swfias.dtos.PersonDto;
import org.swfias.dtos.StaffDto;
import org.swfias.enums.PersonType;
import org.swfias.utils.PersonServiceHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PersonService {
    private PersonDao personDao;

    protected static final Logger logger = LogManager.getLogger(PersonService.class);


    public PersonService(PersonDao personDao) {

        this.personDao = personDao;
    }

    public Optional<PersonDto> getPersonById(long userId) {
        Optional<PersonDto> persons = null;
        try {
            persons = personDao.getById(userId);
        } catch (Exception e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }
        return persons;
    }

    public List<PersonDto> getAllPersons() {
        List<PersonDto> persons = new ArrayList<>();
        try {
            persons = personDao.getAll();
        } catch (Exception e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }
        return persons;
    }

    public boolean addNewPerson(PersonType type, String firstName, String lastName, String password, String email, String address, String phoneNumber) {

        try {
            PersonServiceHelper.validatePersonParameters(type, firstName, lastName, password, email, phoneNumber);
            PersonDto personDto = null;
            if (type == PersonType.ADMIN) {
                personDto = new AdminDto(firstName, lastName, password, email, address, phoneNumber);
            } else if (type == PersonType.STAFF) {
                personDto = new StaffDto(firstName, lastName, password, email, address, phoneNumber);
            } else if (type == PersonType.CUSTOMER) {
                personDto = new CustomerDto(firstName, lastName, password, email, address, phoneNumber);
            }
            personDao.save(personDto);
            logger.info("Person has been added. Person Information: " + personDto);
            return true;
        } catch (SQLException e) {
            logger.error("Exception has been Occurred. Details: " + e);


        } catch (IllegalArgumentException e) {
            logger.error("Exception has been Occurred. Details: " + e);

        }
        return false;
    }

    public PersonDto checkUserLogin(String userId, String password) {
        PersonDto personDto = null;
        try {
            PersonServiceHelper.validatePersonLoginParameters(userId, password);
            personDto = personDao.LoginCheck(userId, password);
            if (personDto == null) {
                logger.warn("Incorrect login information");
            }
        } catch (Exception e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }

        return personDto;
    }


}
