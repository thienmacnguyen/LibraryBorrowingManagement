package com.macthien.LibraryBorrowingManagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRequest {
    @NotBlank
    private String bookCode;

    @NotBlank
    private String title;

    @NotNull
    @Min(value = 1)
    private Long totalCopies;
}
