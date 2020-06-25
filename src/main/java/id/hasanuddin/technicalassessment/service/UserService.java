package id.hasanuddin.technicalassessment.service;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import id.hasanuddin.technicalassessment.config.security.JwtTokenProvider;
import id.hasanuddin.technicalassessment.exception.AuthenticationException;
import id.hasanuddin.technicalassessment.exception.UserAlreadyExistsException;
import id.hasanuddin.technicalassessment.external.UserRepository;
import id.hasanuddin.technicalassessment.model.User;
import id.hasanuddin.technicalassessment.request.AuthenticationRequest;
import id.hasanuddin.technicalassessment.request.UserRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	public User createUser(UserRequest request) {
		checkEmail(request);
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.roles(Collections.singletonList("ROLE_USER"))
				.build();
		return userRepository.save(user);
	}

	private void checkEmail(UserRequest request) {
		if (userRepository.emailExists(request.getEmail())) {
			throw new UserAlreadyExistsException(request.getEmail());
		}
	}

	public String createToken(AuthenticationRequest request) {
		try {
            String username = request.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));
            User user = userRepository.loadUserByUsername(username);
            return jwtTokenProvider.createToken(user);
        } catch (Exception e) {
            throw new AuthenticationException("Invalid email/password supplied");
        }
	}

}
