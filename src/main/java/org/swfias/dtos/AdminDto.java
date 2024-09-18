package org.swfias.dtos;

import lombok.NoArgsConstructor;
import org.swfias.enums.PersonType;

@NoArgsConstructor
public class AdminDto extends PersonDto {
    public AdminDto(String firstName, String lastName, String password, String email, String address, String phoneNumber) {
        super(PersonType.ADMIN, firstName, lastName, password, email, address, phoneNumber);
    }
}
