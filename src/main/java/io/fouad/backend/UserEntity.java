package io.fouad.backend;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

@SuppressWarnings("JpaEntityGraphsInspection")
@Table(name = "users")
@Entity
@NamedEntityGraphs({
	@NamedEntityGraph(name = "User",
		attributeNodes = {
			@NamedAttributeNode("creatorUser"),
			@NamedAttributeNode("updaterUser")
		}
	)
})
public class UserEntity extends AuditableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	Integer id;
	
	@NotNull
	@Column(name = "username")
	String username;
	
	@NotNull
	@Column(name = "hashed_password")
	String hashedPassword;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role_id", columnDefinition = "INT")
	Set<User.Role> roles;
	
	@OneToMany(mappedBy = "user", cascade = {PERSIST, MERGE, REFRESH, DETACH}, fetch = FetchType.LAZY) // inverse side
	List<UserRoleEntity> userRoles;
	
	public User toUser() {
		return new SpelAwareProxyProjectionFactory().createProjection(User.class, this);
	}
	
	public UserSimpleInfo toUserSimpleInfo() {
		return new SpelAwareProxyProjectionFactory().createProjection(UserSimpleInfo.class, this);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserEntity)) return false;
		return id != null && id.equals(((UserEntity) o).getId());
	}
	
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public Set<User.Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<User.Role> roles) {
		this.roles = roles;
	}

	public List<UserRoleEntity> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRoleEntity> userRoles) {
		this.userRoles = userRoles;
	}
}