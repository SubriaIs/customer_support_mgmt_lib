package org.swfias;

import org.swfias.daos.CaseDao;
import org.swfias.daos.PersonDao;
import org.swfias.dtos.*;
import org.swfias.enums.PersonType;
import org.swfias.enums.SeverityType;
import org.swfias.enums.StatusType;
import org.swfias.services.CaseService;
import org.swfias.services.PersonService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) throws SQLException {
        PersonDao personDao = new PersonDao();
        CaseDao caseDao = new CaseDao();
        Date createdDate = new Date(System.currentTimeMillis());
        CaseDto caseDto = new CaseDto(2, "ndclknd", "dmvmskdvm", createdDate, StatusType.OPEN, SeverityType.MEDIUM, -1, null, null);

        personDao.clearAll();
        HashMap<String, PersonDao> personDaoList = new HashMap<>();
        personDaoList.put(null, null);
        if (personDaoList == null) {
            System.out.println(")");
        }


        String userid = "3458cgi";
        String userpass = "sl9976.0?2456";


        PersonService personService = new PersonService(personDao);
        personService.addNewPerson(PersonType.STAFF, "Salim", "khan", "3458cgi9", "abc@email.com", "koi 5", "04566879");
        personService.addNewPerson(PersonType.CUSTOMER, "Karim", "Khan", "1234567a", "asbc@email.com", "koi 5", "8503839");
        personService.addNewPerson(PersonType.ADMIN, "Cooca", "Khan", "12345678a", "cobc@email.com", "koi 5", "68943087");


        CaseService caseService = new CaseService(caseDao, personDao);
        caseService.addNewCase(caseDto);
        System.out.println(personService.getAllPersons().toString());
        System.out.println(caseDao.getAll().toString());
        System.out.println(caseService.getAllCases().toString());


    }
}