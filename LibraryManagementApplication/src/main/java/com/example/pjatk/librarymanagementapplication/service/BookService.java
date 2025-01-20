package com.example.pjatk.librarymanagementapplication.service;

import java.util.List;

import com.example.pjatk.librarymanagementapplication.model.enums.BookCondition;
import org.springframework.stereotype.Service;

import com.example.pjatk.librarymanagementapplication.model.Book;
import com.example.pjatk.librarymanagementapplication.repository.BookRepository;

@Service
public class BookService {
    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        if (book.getIsbn() == null) {
            throw new IllegalArgumentException("ISBN is required field");
        }
        if (book.getBookCondition() == null) {
            book.setBookCondition(BookCondition.NEW); // Default condition
        }
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Could not find Book by ID: " + id);
        }
        return bookRepository.findById(id).get();
    }

    public Book updateBook(Long actualBookId, Book updatedBook) {
        Book actualBook = getBookById(actualBookId);

        if (!actualBook.getBookType().equals(updatedBook.getBookType()) && updatedBook.getBookType() != null) {
            actualBook.setBookType(updatedBook.getBookType());
        }

        if (updatedBook.getBookCondition() != null && !actualBook.getBookCondition().equals(updatedBook.getBookCondition())) {
            actualBook.setBookCondition(updatedBook.getBookCondition());
        }

        return bookRepository.save(actualBook);
    }

    public void deleteBook(Long id){
        getBookById(id);
        bookRepository.deleteById(id);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}

