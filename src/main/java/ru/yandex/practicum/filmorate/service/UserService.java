package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;
import ru.yandex.practicum.filmorate.exception.EntityAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        Set<Long> friends = user.getFriends();
        friends.add(friend.getId());
        Set<Long> friendFriends = friend.getFriends();
        friendFriends.add(user.getId());
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User addUser(User user) throws EntityAlreadyExistException {
        return userStorage.addUser(user);
    }

    public User updateUser(Long userId, User user) {
        return userStorage.updateUser(userId, user);
    }

    public User getUser(Long id) {
        return userStorage.getUser(id);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        Set<Long> friends = user.getFriends();
        friends.remove(friend.getId());
        Set<Long> friendFriends = friend.getFriends();
        friendFriends.remove(user.getId());
    }

    public List<User> getFriends(Long userId) {
        User user = userStorage.getUser(userId);
        List<User> friends = user.getFriends().stream()
                .map(x -> userStorage.getUser(x))
                .collect(Collectors.toList());
        return friends;
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = userStorage.getUser(userId);
        User friend = userStorage.getUser(friendId);
        Set<Long> friends = user.getFriends();
        Set<Long> friendFriends = friend.getFriends();
        Set<Long> commonFriends = new HashSet<Long>(friends);
        commonFriends.retainAll(friendFriends);
        return commonFriends.stream()
                .map(x -> userStorage.getUser(x))
                .collect(Collectors.toList());
    }
}