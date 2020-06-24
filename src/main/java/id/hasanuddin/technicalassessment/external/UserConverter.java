package id.hasanuddin.technicalassessment.external;

import org.springframework.stereotype.Component;

import id.hasanuddin.technicalassessment.model.User;

@Component
public class UserConverter {

	public User fromEntity(UserEntity user) {
		return User.builder()
				.id(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRoles())
				.build();
	}
	
	public UserEntity toEntity(User user) {
		return UserEntity.builder()
				.id(user.getId())
				.email(user.getEmail())
				.password(user.getPassword())
				.roles(user.getRoles())
				.build();
	}
}
