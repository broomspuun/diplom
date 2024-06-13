package com.example.diplom.service;

import com.example.diplom.model.User;
import com.example.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // возвращает всех пользователей
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // ищет пользователя по id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // удаляет пользователя по id
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // сохраняет пользователя при этом хэшируя пароль
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    // ищет пользователя по имени
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}