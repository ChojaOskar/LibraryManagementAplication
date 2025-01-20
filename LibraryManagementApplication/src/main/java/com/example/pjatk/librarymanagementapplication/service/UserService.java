package com.example.pjatk.librarymanagementapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pjatk.librarymanagementapplication.model.User;
import com.example.pjatk.librarymanagementapplication.repository.UserRepository;
import com.example.pjatk.librarymanagementapplication.model.enums.MembershipType;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        if (user.getMembershipType() == null) {
            user.setMembershipType(MembershipType.STANDARD); // Default membership
        }

        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Could not find User with ID: " + id);
        }
        return userRepository.findById(id).get();
    }

    public void deleteUser(Long id){
        userRepository.delete(getUserById(id));
    }

    public User updateUser(Long actualUserId, User updatedUser){
        User actualUser = getUserById(actualUserId);

        if(!actualUser.getActive() == updatedUser.getActive()){
            actualUser.setActive(updatedUser.getActive());
        }

        if(!actualUser.getRole().equals(updatedUser.getRole())){
            actualUser.setRole(updatedUser.getRole());
        }

        if (updatedUser.getMembershipType() != null && !actualUser.getMembershipType().equals(updatedUser.getMembershipType())) {
            actualUser.setMembershipType(updatedUser.getMembershipType());
        }

        return userRepository.save(actualUser);

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}

