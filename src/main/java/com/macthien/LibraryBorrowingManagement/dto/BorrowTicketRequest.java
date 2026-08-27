package com.macthien.LibraryBorrowingManagement.dto;

import com.macthien.LibraryBorrowingManagement.enums.BorrowTicketStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class BorrowTicketRequest {
    private String readerCode;

    private String bookCode;

    private BorrowTicketStatus borrowTicketStatus;

    private LocalDateTime borrowedAt;

    private LocalDateTime returnedAt;
}
