package com.example.hospital.repository;

import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    List<User> getUsersByRoleEquals(Role role);

    User getUserById(long id);

    List<User> getUserByDoctor(User user);

    @Query(value = "SELECT *FROM users WHERE users.card_id in " +
            "(select distinct(a.card_id) from assignment_nursehelper an " +
            "join assignment a on a.id = an.assignment_id where an.nurse_id = ?1)", nativeQuery = true)
    List<User> getUsersByNurseId(long id);


}