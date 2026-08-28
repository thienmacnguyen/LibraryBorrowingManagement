package com.macthien.LibraryBorrowingManagement.controller;

import com.macthien.LibraryBorrowingManagement.dto.BorrowTicketRequest;
import com.macthien.LibraryBorrowingManagement.dto.BorrowTicketResponse;
import com.macthien.LibraryBorrowingManagement.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrows")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowTicketResponse> borrowBook(@RequestBody @Valid BorrowTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowService.borrowBook(request));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowTicketResponse> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.returnBook(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowTicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowService.getTicketById(id));
    }
}
