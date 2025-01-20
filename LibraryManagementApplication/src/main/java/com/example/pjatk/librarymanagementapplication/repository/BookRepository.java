package com.example.pjatk.librarymanagementapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pjatk.librarymanagementapplication.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}