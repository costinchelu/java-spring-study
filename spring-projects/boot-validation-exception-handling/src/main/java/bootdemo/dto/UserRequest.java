package bootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Username not provided")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^\\d{10}$",message = "Invalid mobile number")
    private String mobile;

    @NotNull(message = "Gender cannot be null")
    private String gender;

    @Min(18)
    @Max(60)
    private int age;

    @NotBlank(message = "Nationality not provided")
    private String nationality;
}
