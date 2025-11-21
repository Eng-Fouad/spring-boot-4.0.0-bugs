package io.fouad.backend;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class UsersController {

	private final UsersService usersService;

	public UsersController(UsersService usersService) {
		this.usersService = usersService;
	}

	@GetMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	public Page<User> getAllUsers(@RequestParam("page_index") @PositiveOrZero Integer pageIndex, // 0 based
                                  @RequestParam("results_per_page") @Min(1) @Max(20) Integer resultsPerPage) {
		return usersService.getAllUsers(pageIndex, resultsPerPage);
	}
}