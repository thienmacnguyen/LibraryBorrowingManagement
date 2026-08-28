package com.macthien.LibraryBorrowingManagement.service;

import com.macthien.LibraryBorrowingManagement.dto.BookRequest;
import com.macthien.LibraryBorrowingManagement.dto.BookResponse;
import com.macthien.LibraryBorrowingManagement.entity.Book;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getBookByCode(String code);
    List<BookResponse> searchBooks(String keyword);
    Book findEntityByCode(String code);
}
