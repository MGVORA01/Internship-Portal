package com.jobportal.repository;

import com.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}