package com.example.pjatk.librarymanagementapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pjatk.librarymanagementapplication.model.Borrow;
import com.example.pjatk.librarymanagementapplication.model.enums.BorrowStatus;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Long > {
    List<Borrow> findAllByBorrowStatus(BorrowStatus loanStatus);
}