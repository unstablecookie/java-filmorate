package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Genre {
    COMEDY("Комедия", 1),
    DRAMA("Драма", 2),
    ANIMATION("Мультфильм", 3),
    THRILLER("Триллер", 4),
    DOCUMENTARY("Документальный", 5),
    ACTION("Боевик", 6);

    private Integer id;
    private String name;

    private static Map<Integer, Genre> FORMAT_MAP = Stream
            .of(Genre.values())
            .collect(Collectors.toMap(x -> x.id, Function.identity()));

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    Genre(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @JsonCreator
    public static Genre fromId(Integer id) {
        return Optional
                .ofNullable(FORMAT_MAP.get(id))
                .orElse(null);
    }

    @JsonCreator
    public static Genre[] fromId(Integer[] ids) {
        Genre[] genres = new Genre[ids.length];
        for (int i = 0; i < ids.length ; i++) {
            genres[i] = FORMAT_MAP.get(ids[i]);
        }
        return genres;
    } 
}