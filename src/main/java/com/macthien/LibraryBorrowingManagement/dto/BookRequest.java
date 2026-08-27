package com.macthien.LibraryBorrowingManagement.dto;

import com.macthien.LibraryBorrowingManagement.enums.BookStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookRequest {
    @NotNull
    private String BookCode;

    @Positive
    private Long totalCopies;

    @Positive
    private Long availableCopies;

    private BookStatus bookStatus;
}
