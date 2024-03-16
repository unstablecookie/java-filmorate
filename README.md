# java-filmorate
**Repository for Filmorate project.**
Веб каталог фильмов , пользователей и их предпочтений. Java Spring Boot 2.7.18
Основной функционал:
- Добавление\удаление фильмов
- Добавление\удаление пользователей
- Возможность ставить like понравившимся фильмам
- Можно добавлять пользователей себе в друзья

ER диаграмма описывающая структуру БД

![er diagram](/filmoratedbH2.png)

Примеры запросов:

_получить email подтвержденных друзей Геннадия_
```
SELECT us.email
FROM users AS us
INNER JOIN 
	(SELECT *
	FROM friends AS f
	INNER JOIN users AS u ON u.user_id = f.user_id
	WHERE name = 'Gena') as s ON us.user_id = s.friends_id
WHERE s.approved = 'true';
```

_получить название худшего фильма о войне по мнению пользователей_
```
SELECT m.name
FROM movies AS m
INNER JOIN likes AS l ON m.film_id = l.likes_id
WHERE movie_genre = 'War'
GROUP BY m.name
ORDER BY COUNT(m.name) DESC
LIMIT 1;
```

_получить всех пользователей с email начинающимся с 'c'_
```
SELECT *
FROM users AS u
WHERE u.email LIKE 'c%';
```

_получить топ 3 названия самых свежих вышедших фильма, начиная с 2015 года по убыванию_
```
SELECT name
FROM movies AS m
WHERE EXTRACT(YEAR FROM m.releasedate) > '2014'
ORDER BY m.releasedate DESC;
```
