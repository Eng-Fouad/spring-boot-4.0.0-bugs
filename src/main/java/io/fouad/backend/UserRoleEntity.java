package io.fouad.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.Instant;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

@Table(name = "user_roles")
@Entity
@IdClass(UserRoleEntity.CompositeKey.class)
@NamedEntityGraphs({
	@NamedEntityGraph(name = "UserRole",
		attributeNodes = {
			@NamedAttributeNode("user")
		}
	)
})
public class UserRoleEntity {
	
	public record CompositeKey(UserEntity user, User.Role role){}
	
	@Id
	@ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH}, fetch = FetchType.LAZY) // owning side
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	UserEntity user;
	
	@Id
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role_id", columnDefinition = "INT")
    User.Role role;
	
	@CreatedDate
	@Column(name = "creation_timestamp", columnDefinition = "DATETIME2")
	Instant creationTimestamp;
	
	public UserRole toUserRole() {
		return new SpelAwareProxyProjectionFactory().createProjection(UserRole.class, this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserRoleEntity)) return false;
		return user != null && role != null &&
                user.equals(((UserRoleEntity) o).getUser()) &&
			   role.equals(((UserRoleEntity) o).getRole());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public User.Role getRole() {
		return role;
	}

	public void setRole(User.Role role) {
		this.role = role;
	}

	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
}