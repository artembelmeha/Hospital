package com.example.hospital.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
@NoArgsConstructor
public class RegistrationInfo {


    @Pattern(regexp = "[A-Z][a-z]+",
            message = "[firstName] Must start with a capital letter followed by one or more lowercase letters")
    @NotNull
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "[lastName] Must start with a capital letter followed by one or more lowercase letters")
    @NotNull
    private String lastName;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "[email] Must be a valid e-mail address")
    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,10}",
            message = "Must be minimum 6 characters, at least: one lowercase" +
                    " latin character, one uppercase latin character and one number")
    private String password;

}
