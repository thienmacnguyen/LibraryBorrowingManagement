package com.macthien.LibraryBorrowingManagement.controller;

import com.macthien.LibraryBorrowingManagement.dto.BookRequest;
import com.macthien.LibraryBorrowingManagement.dto.BookResponse;
import com.macthien.LibraryBorrowingManagement.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @GetMapping("/{code}")
    public ResponseEntity<BookResponse> getBookByCode(@PathVariable String code) {
        return ResponseEntity.ok(bookService.getBookByCode(code));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(bookService.searchBooks(keyword));
    }
}
