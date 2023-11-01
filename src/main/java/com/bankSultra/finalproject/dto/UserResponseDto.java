package com.bankSultra.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto implements Serializable {

    private Long id;


    private String email;
//    private String password;
//    private String firstName;
//    private String lastName;
//    private String mobileNumber;
}
