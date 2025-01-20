package com.example.pjatk.librarymanagementapplication.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import com.example.pjatk.librarymanagementapplication.model.enums.BorrowStatus;

@Entity
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime borrowStartDate;
    private LocalDateTime expectedBorrowEndDate;
    private LocalDateTime actualBorrowEndDate;
    private BorrowStatus BorrowStatus;
    @OneToOne
    private User BorrowTo;
    @OneToOne
    private User employee;
    @OneToOne
    private com.example.pjatk.librarymanagementapplication.model.Book BorrowBook;

    public Borrow() {
    }

    public Borrow(User borrowTo, User employee, com.example.pjatk.librarymanagementapplication.model.Book borrowBook, LocalDateTime actualBorrowEndDate) {
        this.borrowStartDate = LocalDateTime.now();
        this.expectedBorrowEndDate = LocalDateTime.now().plusDays(7);
        this.BorrowStatus = BorrowStatus.DURING_BORROW;
        this.BorrowTo = borrowTo;
        this.employee = employee;
        this.actualBorrowEndDate = actualBorrowEndDate;
        this.BorrowBook = borrowBook;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getBorrowStartDate() {
        return borrowStartDate;
    }

    public LocalDateTime getExpectedBorrowEndDate() {
        return expectedBorrowEndDate;
    }

    public LocalDateTime getActualBorrowEndDate() {
        return actualBorrowEndDate;
    }

    public BorrowStatus getBorrowStatus() {
        return BorrowStatus;
    }

    public User getBorrowTo() {
        return BorrowTo;
    }

    public User getEmployee() {
        return employee;
    }

    public com.example.pjatk.librarymanagementapplication.model.Book getLoanBook() {
        return BorrowBook;
    }

    public void setActualBorrowEndDate(LocalDateTime actualBorrowEndDate) {
        this.actualBorrowEndDate = actualBorrowEndDate;
    }

    public void setBorrowStatus(BorrowStatus borrowStatus) {
        this.BorrowStatus = borrowStatus;
    }

    public void setBorrowTo(User borrowTo) {
        this.BorrowTo = borrowTo;
    }
}



