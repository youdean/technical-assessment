package id.hasanuddin.technicalassessment.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AuthenticationRequest {

    @ApiModelProperty(required = true, example = "user@domain.com")
    @Email
    @NotBlank
    private String email;

    @ApiModelProperty(required = true, example = "Password123!")
    @NotBlank
    private String password;

}