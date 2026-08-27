package com.macthien.LibraryBorrowingManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Reader")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private long readerId;

    @Column(name = "reader_code")
    private String readerCode;

    @Column(name = "reader_name")
    private String readerName;
}
