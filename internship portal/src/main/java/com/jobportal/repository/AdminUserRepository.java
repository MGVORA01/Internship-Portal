package com.jobportal.repository;

import com.jobportal.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer> {
    AdminUser findByEmailAndPassword(String email, String password);
}
