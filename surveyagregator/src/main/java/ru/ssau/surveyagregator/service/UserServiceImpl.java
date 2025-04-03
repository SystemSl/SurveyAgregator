package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.User;
import ru.ssau.surveyagregator.repository.UserRepository;
import ru.ssau.surveyagregator.requests.UserFormRequest;
import ru.ssau.surveyagregator.requests.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean registerAdmin(String name, String email, String password) {
        //String encodedPassword = passwordEncoder.encode(password);
        User newUser = User.builder().username(name).password(password).email(email).build();
        userRepository.save(newUser);
        return true;
    }

    @Transactional
    public boolean registerAdmin(User newUser) {
        //newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return true;
    }

    @Transactional
    public boolean registerAdmin(UserFormRequest admin) {
        User newUser = User.builder().username(admin.getName()).password(admin.getPassword()).email(admin.getEmail()).build();
        //newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return true;
    }

    @Transactional
    public User findById(UUID id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public List<User> findAllById(List<UUID> ids) {
        return userRepository.findAllById(ids);
    }

    @Transactional
    public boolean update(UserUpdateRequest request, User user) {
        user.setEmail(request.getEmail());
        user.setUsername(request.getName());
        userRepository.save(user);
        return true;
    }

    public void clear() {
        userRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден"));
    }

    @Override
    public boolean existsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return true;
        }
        return false;
    }
}
