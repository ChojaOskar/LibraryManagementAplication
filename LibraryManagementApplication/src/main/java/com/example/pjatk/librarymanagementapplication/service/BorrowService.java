package com.example.pjatk.librarymanagementapplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.pjatk.librarymanagementapplication.model.Borrow;
import com.example.pjatk.librarymanagementapplication.model.enums.BorrowStatus;
import com.example.pjatk.librarymanagementapplication.repository.BorrowRepository;

@Service
public class BorrowService {

    private BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> getAllBorrowsByStatus(BorrowStatus loanStatus) {
        return borrowRepository.findAllByBorrowStatus(loanStatus);
    }

    public Borrow createBorrow(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public Optional<Borrow> getBorrowById(Long id){
        return borrowRepository.findById(id);
    }

    public Borrow updateBorrow(Borrow updatedBorrow, Long id) {
        Borrow actualBorrow = getBorrowById(id).get();

        if(!actualBorrow.getBorrowTo().getId().equals(updatedBorrow.getBorrowTo().getId())){
            actualBorrow.setBorrowTo(updatedBorrow.getBorrowTo());
        }

        if(!actualBorrow.getActualBorrowEndDate().equals(updatedBorrow.getActualBorrowEndDate())){
            actualBorrow.setActualBorrowEndDate(updatedBorrow.getActualBorrowEndDate());
        }

        if(!actualBorrow.getBorrowStatus().equals(updatedBorrow.getBorrowStatus())){
            actualBorrow.setBorrowStatus(updatedBorrow.getBorrowStatus());
        }

        return borrowRepository.save(actualBorrow);
    }
}

