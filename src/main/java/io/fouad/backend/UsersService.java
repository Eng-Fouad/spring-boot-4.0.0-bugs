package io.fouad.backend;

import org.springframework.data.domain.Page;

public interface UsersService {
    Page<User> getAllUsers(int pageIndex, int resultsPerPage);
}