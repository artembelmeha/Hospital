package com.example.hospital.service.impl;

import com.example.hospital.exception.NullEntityReferenceException;
import com.example.hospital.model.Role;
import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException("User with id " + id + " not found");
    }

    @Override
    public User update(User user) {
        if (user != null) {
            User oldUser = readById(user.getId());
            if (oldUser != null) {
                return userRepository.save(user);
            }
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
        if (user != null) {
            userRepository.delete(user);
        } else {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllUserWithoutRole() {
        return userRepository.getUsersByRoleEquals(null);
    }
    @Override
    public List<User> getAllNurse() {
        return userRepository.getUsersByRoleEquals(Role.NURSE);
    }
// Spring security
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.getUserByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not Found!");
//        }
//        return user;
//    }
}
