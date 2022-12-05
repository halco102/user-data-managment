package com.user.data.management.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @Size(min = 1, max = 50)
    @NotNull
    @NotBlank
    private String emailOrUsername;

    @Size(min = 8, max = 20)
    private String password;

}
