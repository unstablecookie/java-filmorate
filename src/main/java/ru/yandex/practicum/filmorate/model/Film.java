package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.boot.convert.DurationUnit;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Data
public class Film {
    Long id;
    @NotBlank(message = "name cannot be blank")
    @NotNull(message = "name cannot be null")
    String name;
    @Size(max = 200)
    String description;
    @Past
    @NotNull(message = "release date cannot be null")
    LocalDate releaseDate;
    @DurationUnit(ChronoUnit.MINUTES)
    Duration duration;

    public void setDuration(long minutes) {
        duration = Duration.ofMinutes(minutes);
    }

    public long getDuration() {
        return duration.toMinutes();
    }
}
