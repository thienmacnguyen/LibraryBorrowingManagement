package com.macthien.LibraryBorrowingManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private String bookCode;
    private String title;
    private Long totalCopies;
    private Long availableCopies;
    private boolean active;
}
