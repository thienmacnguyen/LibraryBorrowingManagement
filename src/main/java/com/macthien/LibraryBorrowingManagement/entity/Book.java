package com.macthien.LibraryBorrowingManagement.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String bookCode;
    private String title;
    private Long totalCopies;
    private Long availableCopies;
    private boolean active = true;
}
