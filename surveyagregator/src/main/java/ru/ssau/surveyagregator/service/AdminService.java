package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Admin;
import ru.ssau.surveyagregator.repository.AdminRepository;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean registerAdmin(String name, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Admin newAdmin = new Admin(name, email, encodedPassword);
        adminRepository.save(newAdmin);
        return true;
    }

    @Transactional
    public boolean registerAdmin(Admin newAdmin) {
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        adminRepository.save(newAdmin);
        return true;
    }

    public void clear() {
        adminRepository.deleteAll();
    }
}
