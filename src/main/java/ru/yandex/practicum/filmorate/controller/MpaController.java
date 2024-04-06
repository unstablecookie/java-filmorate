package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.model.Mpa;
import java.util.List;
import ru.yandex.practicum.filmorate.util.LogThis;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mpa")
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    @LogThis
    public List<Mpa> getMpas() {
        return mpaService.getMpas();
    }

    @GetMapping(value = "/{id}")
    @LogThis
    public Mpa getMpa(@PathVariable(required = true) Long id) {
        return mpaService.getMpa(id);
    }
}