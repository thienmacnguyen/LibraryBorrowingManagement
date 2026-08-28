package com.macthien.LibraryBorrowingManagement.service.ServiceImpl;

import com.macthien.LibraryBorrowingManagement.dto.BookRequest;
import com.macthien.LibraryBorrowingManagement.dto.BookResponse;
import com.macthien.LibraryBorrowingManagement.entity.Book;
import com.macthien.LibraryBorrowingManagement.exception.ConflictException;
import com.macthien.LibraryBorrowingManagement.exception.NotFoundException;
import com.macthien.LibraryBorrowingManagement.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookServiceImpl implements BookService {
    private final Map<String, Book> bookStorage = new ConcurrentHashMap<>();
    @Override
    public BookResponse createBook(BookRequest request) {
        if (bookStorage.containsKey(request.getBookCode())) {
            throw new ConflictException("Mã đã tồn tại: " + request.getBookCode());
        }

        Book book = new Book(
                request.getBookCode(),
                request.getTitle(),
                request.getTotalCopies(),
                request.getTotalCopies(),
                true
        );

        bookStorage.put(book.getBookCode(), book);
        return mapToResponse(book);
    }

    @Override
    public BookResponse getBookByCode(String code) {
        return mapToResponse(findEntityByCode(code));
    }

    @Override
    public List<BookResponse> searchBooks(String keyword) {

        List<BookResponse> results = new ArrayList<>();

        for (Book book : bookStorage.values()) {

            if (keyword == null || keyword.isBlank()
                    || book.getTitle().toLowerCase()
                    .contains(keyword.trim().toLowerCase())) {

                results.add(mapToResponse(book));
            }
        }

        return results;
    }

    @Override
    public Book findEntityByCode(String code) {
        Book book = bookStorage.get(code);
        if (book == null) {
            throw new NotFoundException("Không tìm thấy sách với mã: " + code);
        }
        return book;
    }

    public BookResponse mapToResponse(Book book) {
        return BookResponse.builder()
                .bookCode(book.getBookCode())
                .title(book.getTitle())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .active(book.isActive())
                .build();
    }
}
