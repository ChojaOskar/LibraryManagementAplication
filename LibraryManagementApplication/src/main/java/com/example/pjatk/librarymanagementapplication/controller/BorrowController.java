package com.example.pjatk.librarymanagementapplication.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import com.example.pjatk.librarymanagementapplication.model.Borrow;
import com.example.pjatk.librarymanagementapplication.model.enums.BorrowStatus;
import com.example.pjatk.librarymanagementapplication.service.BorrowService;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    private BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping("")
    public ResponseEntity<List<Borrow>> getAllLoansByStatus(@PathParam("loanStatus") BorrowStatus borrowStatus){
        return ResponseEntity.ok(borrowService.getAllBorrowsByStatus(borrowStatus));
    }

    @PostMapping("")
    public ResponseEntity<Borrow> createBorrow(@RequestBody Borrow borrow){
        return ResponseEntity.ok(borrowService.createBorrow(borrow));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Borrow> updateLoan(@RequestBody Borrow updatedBorrow, @PathVariable("id") Long id){
        return ResponseEntity.ok(borrowService.updateBorrow(updatedBorrow, id));
    }
}

