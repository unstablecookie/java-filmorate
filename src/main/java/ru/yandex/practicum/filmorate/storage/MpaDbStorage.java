package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository("mpaDbStorage")
@Slf4j
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getMpas() {
        String request = "select * from mpa";
        return jdbcTemplate.query(request, (rs, rowNum) -> mapMpa(rs))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Mpa getMpa(Integer id) {
        Mpa mpa = Mpa.fromId(id);
        if (mpa == null) {
            throw new EntityNotFoundException("mpa not found");
        }
        return mpa;
    }

    private Mpa mapMpa(ResultSet rs) throws SQLException {
        int mpaId = rs.getInt("mpa_id");
        return Mpa.fromId(mpaId);
    }
}