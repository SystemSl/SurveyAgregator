package ru.ssau.surveyagregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ssau.surveyagregator.model.Admin;
import ru.ssau.surveyagregator.repository.AdminRepository;
import ru.ssau.surveyagregator.requests.AdminFormRequest;
import ru.ssau.surveyagregator.requests.AdminUpdateRequest;

import java.util.List;

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
        Admin newAdmin = Admin.builder().name(name).password(password).email(email).build();
        adminRepository.save(newAdmin);
        return true;
    }

    @Transactional
    public boolean registerAdmin(Admin newAdmin) {
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        adminRepository.save(newAdmin);
        return true;
    }

    @Transactional
    public boolean registerAdmin(AdminFormRequest admin) {
        Admin newAdmin = Admin.builder().name(admin.getName()).password(admin.getPassword()).email(admin.getEmail()).build();
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        adminRepository.save(newAdmin);
        return true;
    }

    @Transactional
    public Admin findById(Integer id) {
        return adminRepository.findById(id).get();
    }

    @Transactional
    public List<Admin> findAllById(List<Integer> ids) {
        return adminRepository.findAllById(ids);
    }

    @Transactional
    public boolean update(AdminUpdateRequest request, Admin admin) {
        admin.setEmail(request.getEmail());
        admin.setName(request.getName());
        adminRepository.save(admin);
        return true;
    }

    public void clear() {
        adminRepository.deleteAll();
    }
}
