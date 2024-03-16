package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.List;

@Repository("genreDbStorage")
@Slf4j
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenres() {
        String request = "select * from genre";
        return jdbcTemplate.query(request, (rs, rowNum) -> mapGenre(rs))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Genre getGenre(Integer id) {
        Genre genre = Genre.fromId(id);
        if (genre == null) {
            throw new EntityNotFoundException("genre not found");
        }
        return Genre.fromId(id);
    }

    private Genre mapGenre(ResultSet rs) throws SQLException {
        int mpaId = rs.getInt("genre_id");
        return Genre.fromId(mpaId);
    }
}