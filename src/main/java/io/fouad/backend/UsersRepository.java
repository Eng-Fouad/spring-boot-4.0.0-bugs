package io.fouad.backend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer> {

    @EntityGraph("User")
    Page<User> queryAllByDeletedIsFalseOrderByIdDesc(Pageable pageable);
}