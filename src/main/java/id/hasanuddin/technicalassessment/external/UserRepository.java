package id.hasanuddin.technicalassessment.external;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import id.hasanuddin.technicalassessment.exception.UserNotFoundException;
import id.hasanuddin.technicalassessment.model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepository implements UserDetailsService {
	
	private final JpaUserRepository users;
	private final UserConverter converter;

	@Override
	public User loadUserByUsername(String email) {
		return users.findByEmail(email)
				.map(converter::fromEntity)
				.orElseThrow(() -> new UserNotFoundException("User with email: " + email + " not found"));
	}
	
	public User save(User newUser) {
		UserEntity entity = users.saveAndFlush(converter.toEntity(newUser));
		return converter.fromEntity(entity);
	}
	
	public boolean emailExists(String email) {
		return users.existsByEmail(email);
	}

}

@Repository
interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);
	Boolean existsByEmail(String email);
}