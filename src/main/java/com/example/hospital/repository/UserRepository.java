package com.example.hospital.repository;

import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
    List<User> getUsersByRoleEquals(Role role);
}

