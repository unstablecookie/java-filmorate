package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.boot.convert.DurationUnit;
import ru.yandex.practicum.filmorate.util.DurationAnnotation;
import ru.yandex.practicum.filmorate.util.ReleaseDateAnnotation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Long id;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @Size(max = 200)
    private String description;
    @ReleaseDateAnnotation
    private LocalDate releaseDate;
    @DurationUnit(ChronoUnit.MINUTES)
    @DurationAnnotation
    private Duration duration;
    private final Set<Long> likes = new HashSet<>();

    public void setDuration(long minutes) {
        duration = Duration.ofMinutes(minutes);
    }

    public long getDuration() {
        return duration.toMinutes();
    }
    
    public int getLikesCount() {
        return likes.size();
    }
}