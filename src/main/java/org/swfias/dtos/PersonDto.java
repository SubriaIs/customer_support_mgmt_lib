package org.swfias.dtos;

import lombok.*;
import org.swfias.enums.PersonType;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PersonDto {

    private long id;
    private PersonType type;
    private String firstName;
    private String lastName;
    private String userId;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;

    protected PersonDto(PersonType type, String firstName, String lastName, String password, String email, String address, String phoneNumber) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = firstName.toLowerCase() + "." + lastName.toLowerCase();
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
