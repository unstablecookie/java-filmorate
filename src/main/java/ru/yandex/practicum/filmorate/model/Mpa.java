package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Mpa {
    G("G", 1),
    PG("PG", 2),
    PG13("PG-13", 3),
    R("R", 4),
    NC17("NC-17", 5);

    private Integer id;
    private String name;

    private static Map<Integer, Mpa> FORMAT_MAP = Stream
            .of(Mpa.values())
            .collect(Collectors.toMap(x -> x.id, Function.identity()));

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    Mpa(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @JsonCreator
    public static Mpa fromId(Integer id) {
        return Optional
                .ofNullable(FORMAT_MAP.get(id))
                .orElse(null);
    }    
}