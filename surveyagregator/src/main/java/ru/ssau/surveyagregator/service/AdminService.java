package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.User;
import ru.ssau.surveyagregator.repository.UserRepository;
import ru.ssau.surveyagregator.requests.AdminFormRequest;
import ru.ssau.surveyagregator.requests.AdminUpdateRequest;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean registerAdmin(String name, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = User.builder().name(name).password(password).email(email).build();
        userRepository.save(newUser);
        return true;
    }

    @Transactional
    public boolean registerAdmin(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return true;
    }

    @Transactional
    public boolean registerAdmin(AdminFormRequest admin) {
        User newUser = User.builder().name(admin.getName()).password(admin.getPassword()).email(admin.getEmail()).build();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
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
    public boolean update(AdminUpdateRequest request, User user) {
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        userRepository.save(user);
        return true;
    }

    public void clear() {
        userRepository.deleteAll();
    }
}
