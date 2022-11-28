package com.user.data.management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {

    @NotBlank
    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotBlank
    @NotNull
    @Size(min = 8, max = 30)
    private String password;

    @NotNull
    @NotBlank
    @Email
    private String email;

}
