package com.example.pjatk.librarymanagementapplication.model;


import com.example.pjatk.librarymanagementapplication.model.enums.BookCondition;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import com.example.pjatk.librarymanagementapplication.model.enums.Category;


import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tile;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Author> author;
    private Long isbn;
    private Category bookType;

    private BookCondition bookCondition;

    public BookCondition getBookCondition() {
        return bookCondition;
    }



    public Book() {
    }

    public Book(String tile, List<Author> author, Long isbn, Category bookType) {
        this.tile = tile;
        this.author = author;
        this.isbn = isbn;
        this.bookType = bookType;
    }

    public String getTile() {
        return tile;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public Long getIsbn() {
        return isbn;
    }

    public Category getBookType() {
        return bookType;
    }

    public void setBookType(Category bookType) {
        this.bookType = bookType;
    }

    public void setBookCondition(BookCondition bookCondition) {
        this.bookCondition = bookCondition;
    }
}
