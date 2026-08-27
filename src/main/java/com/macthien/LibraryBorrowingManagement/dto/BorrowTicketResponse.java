package com.macthien.LibraryBorrowingManagement.dto;

import com.macthien.LibraryBorrowingManagement.enums.BorrowTicketStatus;

import java.time.LocalDateTime;

public class BorrowTicketResponse {
    private Long borrowTicketId;
    private String readerCode;
    private String bookCode;
    private BorrowTicketStatus borrowTicketStatus;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
}
