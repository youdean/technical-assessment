package id.hasanuddin.technicalassessment.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.hasanuddin.technicalassessment.request.AuthenticationRequest;
import id.hasanuddin.technicalassessment.response.TokenResponse;
import id.hasanuddin.technicalassessment.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@Api(tags = "Auth")
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/token", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public TokenResponse signIn(@RequestBody @Valid AuthenticationRequest credentials) {
        String token = userService.createToken(credentials);
        return new TokenResponse(token);
    }

}