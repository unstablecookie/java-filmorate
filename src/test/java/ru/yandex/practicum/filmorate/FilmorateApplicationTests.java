package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Duration;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.time.LocalDate;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.errors.*;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;

@SpringBootTest
class FilmorateApplicationTests {
	static final LocalDate INITIAL_DATE = LocalDate.of(1895, 12, 28);
	@Autowired
	private Validator validator;
	@Autowired
	private UserController userController;
	@Autowired
	private FilmController filmController;

	@BeforeEach
	void reinitializeControllers() {
		userController = new UserController();
		filmController = new FilmController();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void userContextLoads_success() {
		assertNotNull(userController);
	}

	@Test
	void filmContextLoads_success() {
		assertNotNull(filmController);
	}

	@Test
	void userAdded_success() throws UserAlreadyExistException {
		//given
		Long id = 1L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		userController.addUser(user);
		List<User> users = userController.getUsers();
		User firstUser = users.get(0);
		//then
		assertNotNull(firstUser);
		assertEquals(id,firstUser.getId());
		assertEquals(email,firstUser.getEmail());
		assertEquals(login,firstUser.getLogin());
		assertEquals(name,firstUser.getName());
		assertEquals(date,firstUser.getBirthday());
	}

	@Test
	void userAdded_failure_userAlreadyExistException() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		userController.addUser(user);
		String secondEmail = "user@user.com";
		String secondLogin = "userLogin";
		String secondName = "userName";
		LocalDate secondDate = LocalDate.of(2000,1,1);
		User secondUser = new User();
		secondUser.setId(id);
		secondUser.setEmail(email);
		secondUser.setLogin(login);
		secondUser.setName(name);
		secondUser.setBirthday(date);
		//then
		assertThrows(UserAlreadyExistException.class, () -> {
			userController.addUser(secondUser); });
	}

	@Test
	void userAdded_success_withMalformedId() throws UserAlreadyExistException {
		Long id = -999L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		userController.addUser(user);
		List<User> users = userController.getUsers();
		User firstUser = users.get(0);
		//then
		assertEquals(1L,firstUser.getId());
	}

	@Test
	void userAdded_failure_withEmptyEmail() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "";
		String login = "userLogin";
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void userAdded_failure_withNullEmail() throws UserAlreadyExistException {
		Long id = 1L;
		String email = null;
		String login = "userLogin";
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void userAdded_failure_withEmptyLogin() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = "";
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void userAdded_failure_withNullLogin() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = null;
		String name = "userName";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void userAdded_success_withEmptyName() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = "";
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		userController.addUser(user);
		List<User> users = userController.getUsers();
		User firstUser = users.get(0);
		//then
		assertEquals(login,firstUser.getName());
	}

	@Test
	void userAdded_success_withNullLogin() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = null;
		LocalDate date = LocalDate.of(2000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		userController.addUser(user);
		List<User> users = userController.getUsers();
		User firstUser = users.get(0);
		//then
		assertEquals(login,firstUser.getName());
	}

	@Test
	void userAdded_failure_withFutureBirthday() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = "userName";
		LocalDate date = LocalDate.of(4000,1,1);
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void userAdded_failure_withNullBirthday() throws UserAlreadyExistException {
		Long id = 1L;
		String email = "user@user.com";
		String login = "userLogin";
		String name = "userName";
		LocalDate date = null;
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setLogin(login);
		user.setName(name);
		user.setBirthday(date);
		//when
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void filmAdded_success() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		//when
		filmController.addFilm(film);
		List<Film> films = filmController.getFilms();
		Film firstFilm = films.get(0);
		//then
		assertNotNull(firstFilm);
		assertEquals(id,firstFilm.getId());
		assertEquals(name,firstFilm.getName());
		assertEquals(description,firstFilm.getDescription());
		assertEquals(releaseDate,firstFilm.getReleaseDate());
		assertEquals(duration.toMinutes(),firstFilm.getDuration());
	}

	@Test
	void filmAdded_failure_filmAlreadyExistException() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		filmController.addFilm(film);
		//when
		Film secondFilm = new Film();
		secondFilm.setId(id);
		secondFilm.setName(name);
		secondFilm.setDescription(description);
		secondFilm.setReleaseDate(releaseDate);
		secondFilm.setDuration(duration.toMinutes());
		//then
		assertThrows(FilmAlreadyExistException.class, () -> {
			filmController.addFilm(secondFilm); });
	}

	@Test
	void filmAdded_failure_invalidFilmDataExceptionWithNegativeDuration() throws InvalidFilmDataException,
			FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		//when
		Duration duration = Duration.ofMinutes(-100);
		film.setDuration(duration.toMinutes());
		//then
		assertThrows(InvalidFilmDataException.class, () -> {
			filmController.addFilm(film); });
	}

	@Test
	void filmAdded_failure_invalidFilmDataExceptionWithZeroDuration() throws InvalidFilmDataException,
			FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		//when
		Duration duration = Duration.ofMinutes(0);
		film.setDuration(duration.toMinutes());
		//then
		assertThrows(InvalidFilmDataException.class, () -> {
			filmController.addFilm(film); });
	}

	@Test
	void filmAdded_failure_invalidFilmDataExceptionBeforeINITIAL_DATE() throws InvalidFilmDataException,
			FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "wars are everywhere";
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setDuration(duration.toMinutes());
		//when
		LocalDate releaseDate = LocalDate.of(1894,1,1);
		film.setReleaseDate(releaseDate);
		//then
		assertThrows(InvalidFilmDataException.class, () -> {
			filmController.addFilm(film); });
	}

	@Test
	void filmAdded_failure_invalidFilmDataExceptionInFuture() throws InvalidFilmDataException,
			FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(4000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setDuration(duration.toMinutes());
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		//when
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void filmAdded_failure_withEmptyName() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "";
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		//when
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void filmAdded_failure_withNullName() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = null;
		String description = "wars are everywhere";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		//when
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		//then
		assertFalse(violations.isEmpty());
	}

	@Test
	void filmAdded_success_withEmptyDescription() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		//when
		filmController.addFilm(film);
		List<Film> films = filmController.getFilms();
		Film firstFilm = films.get(0);
		//then
		assertNotNull(firstFilm);
		assertEquals(id,firstFilm.getId());
		assertEquals(name,firstFilm.getName());
		assertEquals(description,firstFilm.getDescription());
		assertEquals(releaseDate,firstFilm.getReleaseDate());
		assertEquals(duration.toMinutes(),firstFilm.getDuration());
	}

	@Test
	void filmAdded_success_withNullDescription() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = "";
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		//when
		filmController.addFilm(film);
		List<Film> films = filmController.getFilms();
		Film firstFilm = films.get(0);
		//then
		assertNotNull(firstFilm);
		assertEquals(id,firstFilm.getId());
		assertEquals(name,firstFilm.getName());
		assertEquals(description,firstFilm.getDescription());
		assertEquals(releaseDate,firstFilm.getReleaseDate());
		assertEquals(duration.toMinutes(),firstFilm.getDuration());
	}

	@Test
	void filmAdded_failure_withTooBigDescription() throws InvalidFilmDataException, FilmAlreadyExistException {
		//given
		Long id = 1L;
		String name = "Wars";
		String description = new String(new char[201]).replace('\0', ' ');
		LocalDate releaseDate = LocalDate.of(2000,1,1);
		Duration duration = Duration.ofMinutes(100);
		Film film = new Film();
		film.setName(name);
		film.setDescription(description);
		film.setReleaseDate(releaseDate);
		film.setDuration(duration.toMinutes());
		//when
		Set<ConstraintViolation<Film>> violations = validator.validate(film);
		//then
		assertFalse(violations.isEmpty());
	}
}