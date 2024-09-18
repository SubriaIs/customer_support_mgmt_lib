package org.swfias.dtos;

import lombok.NoArgsConstructor;
import org.swfias.enums.PersonType;

@NoArgsConstructor
public class StaffDto extends PersonDto {
    public StaffDto(String firstName, String lastName, String password, String email, String address, String phoneNumber) {
        super(PersonType.STAFF, firstName, lastName, password, email, address, phoneNumber);
    }
}
