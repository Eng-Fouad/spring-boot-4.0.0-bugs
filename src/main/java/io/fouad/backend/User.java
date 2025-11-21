package io.fouad.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.Optional;

public interface User {
	
	enum Role {
		UNUSED,
		VIEWER, // 1
		WRITER, // 2
		ADMIN, // 3
	}
	
	Integer getId();
	String getUsername();
	@JsonIgnore String getHashedPassword();
	Boolean getDeleted();
    UserSimpleInfo getCreatorUser();
	Optional<UserSimpleInfo> getUpdaterUser();
	Instant getCreationTimestamp();
	Optional<Instant> getUpdateTimestamp();
}