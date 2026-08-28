package com.macthien.LibraryBorrowingManagement.entity;

import com.macthien.LibraryBorrowingManagement.enums.BorrowTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reader {
    private String readerCode;
    private String bookCode;

}
