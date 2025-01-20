package com.example.pjatk.librarymanagementapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pjatk.librarymanagementapplication.model.User;
import com.example.pjatk.librarymanagementapplication.model.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByRole(UserRole userRole);
    List<User> findAllByActive(boolean isActive);
}
