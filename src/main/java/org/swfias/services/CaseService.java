package org.swfias.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.swfias.daos.CaseDao;
import org.swfias.daos.PersonDao;
import org.swfias.dtos.CaseDto;
import org.swfias.dtos.PersonDto;
import org.swfias.enums.PersonType;
import org.swfias.utils.CaseServiceHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaseService {
    private CaseDao caseDao;
    private PersonDao personDao;
    protected static final Logger logger = LogManager.getLogger(CaseService.class);

    public CaseService(CaseDao caseDao, PersonDao personDao) {
        this.caseDao = caseDao;
        this.personDao = personDao;
    }

    public List<CaseDto> getAllCases() {
        List<CaseDto> cases = new ArrayList<>();
        try {
            cases = caseDao.getAll();
        } catch (Exception e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }
        return cases;
    }

    public List<CaseDto> getAllById(long userId) {
        List<CaseDto> cases = new ArrayList<>();
        try {
            cases = caseDao.getAllById(userId);
        } catch (Exception e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }
        return cases;
    }

    public List<CaseDto> getByAssignedToId(long assignedTo) {
        List<CaseDto> cases = new ArrayList<>();
        try {
            cases = caseDao.getAllByAssignToId(assignedTo);
        } catch (Exception e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }
        return cases;
    }

    public CaseDto getCaseByTitle(String title) {
        try {
            return caseDao.getByTitle(title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addNewCase(CaseDto caseObject) {
        CaseServiceHelper.validateCaseParameters(caseObject.getCreatedBy(), caseObject.getTitle(), caseObject.getDescription(), caseObject.getCreatedDate(), caseObject.getStatus(), caseObject.getSeverity());
        try {
            if (caseDao.isTitleDuplicate(caseObject.getTitle())) {
                throw new IllegalArgumentException("A case with this title already exists.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            List<PersonDto> persons = personDao.getByType(String.valueOf(PersonType.STAFF));
            if (persons != null) {
                PersonDto p = persons.get(new Random().nextInt(persons.size()));
                caseObject.setAssignedTo(p.getId());
                logger.info("Assign case " + caseObject.getTitle() + "to Employee: " + p.getFirstName() + " " + p.getLastName());
                caseDao.save(caseObject);
                return true;
            }

        } catch (SQLException e) {
            logger.error("Exception has been Occurred. Details: " + e);
        }
        return false;
    }

    public boolean updateCase(CaseDto caseDto) {
        return caseDao.updateCase(caseDto);
    }

}
