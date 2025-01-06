package com.eventmanager.eventmanager.dto;

import com.eventmanager.eventmanager.model.enmus.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for creating and updating users")
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private Date dob;
    private Role role;
    private boolean verified ;
    private boolean accountLocked = false ;
}
