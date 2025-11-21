package io.fouad.backend;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Service
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final JsonMapper jsonMapper;

	@Lazy
	public UsersServiceImpl(UsersRepository usersRepository, JsonMapper jsonMapper) {
		this.usersRepository = usersRepository;
		this.jsonMapper = jsonMapper;
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
	public Page<User> getAllUsers(int pageIndex, int resultsPerPage) {
		return usersRepository.queryAllByDeletedIsFalseOrderByIdDesc(PageRequest.of(pageIndex, resultsPerPage));
	}
}