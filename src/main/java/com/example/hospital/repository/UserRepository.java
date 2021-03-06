package com.example.hospital.repository;

import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    List<User> getUsersByRoleEquals(Role role);

    boolean existsByEmail(String email);

    List<User> getUsersByRoleEqualsAndIdNotIn(Role role, Set<Long> excludedIds);

    User getUserById(long id);

    Page<User> getUserByDoctor(User user, Pageable pageable);

    @Query(value = "SELECT *FROM users WHERE users.card_id in " +
            "(select distinct(a.card_id) from assignment_nursehelper an " +
            "join assignment a on a.id = an.assignment_id where an.nurse_id = ?1)", nativeQuery = true)
    List<User> getUsersByNurseId(long id);

    @Query(value = "SELECT * FROM users WHERE card_id = ?1", nativeQuery = true)
    User getUserByMedicalCardId(long id);

    Page<User> findAllByRole(Role role, Pageable pageable);

}