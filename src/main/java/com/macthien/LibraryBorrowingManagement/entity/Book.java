package com.macthien.LibraryBorrowingManagement.entity;

import com.macthien.LibraryBorrowingManagement.enums.BookStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "book_code")
    private String bookCode;

    @Column(name = "total_copies")
    private  Long totalCopies;

    @Column(name = "available_copies")
    private Long availableCopies;

    @Column(name = "status")
    private BookStatus status;
}
