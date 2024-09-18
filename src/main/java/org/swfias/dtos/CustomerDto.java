package org.swfias.dtos;

import lombok.NoArgsConstructor;
import org.swfias.enums.PersonType;

@NoArgsConstructor
public class CustomerDto extends PersonDto {

    public CustomerDto(String firstName, String lastName, String password, String email, String address, String phoneNumber) {
        super(PersonType.CUSTOMER, firstName, lastName, password, email, address, phoneNumber);
    }
}
