package com.macthien.LibraryBorrowingManagement.dto;

import com.macthien.LibraryBorrowingManagement.enums.BookStatus;

public class BookResponse {

    private Long bookId;
    private String bookCode;
    private Long totalCopies;
    private Long availableCopies;
    private BookStatus bookStatus;
}
