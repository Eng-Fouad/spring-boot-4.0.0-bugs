package io.fouad.backend;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {
	
	@NotNull
	@Column(name = "deleted")
	Boolean deleted = Boolean.FALSE;
	
	@CreatedBy
	@ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH}, fetch = FetchType.LAZY) // owning side
	@JoinColumn(name = "creator_user_id", referencedColumnName = "id")
	UserEntity creatorUser;
	
	@LastModifiedBy
	@ManyToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH}, fetch = FetchType.LAZY) // owning side
	@JoinColumn(name = "updater_user_id", referencedColumnName = "id", insertable = false)
    UserEntity updaterUser;
	
	@CreatedDate
	@Column(name = "creation_timestamp", columnDefinition = "DATETIME2")
	Instant creationTimestamp;
	
	@LastModifiedDate
	@Column(name = "update_timestamp", columnDefinition = "DATETIME2", insertable = false)
	Instant updateTimestamp;

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public UserEntity getCreatorUser() {
		return creatorUser;
	}

	public void setCreatorUser(UserEntity creatorUser) {
		this.creatorUser = creatorUser;
	}

	public UserEntity getUpdaterUser() {
		return updaterUser;
	}

	public void setUpdaterUser(UserEntity updaterUser) {
		this.updaterUser = updaterUser;
	}

	public Instant getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Instant creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Instant getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Instant updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
}