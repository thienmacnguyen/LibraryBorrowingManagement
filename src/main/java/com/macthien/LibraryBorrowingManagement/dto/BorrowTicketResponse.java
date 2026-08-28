package com.macthien.LibraryBorrowingManagement.dto;

import com.macthien.LibraryBorrowingManagement.enums.BorrowTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowTicketResponse {
    private Long borrowTicketId;
    private String readerCode;
    private String bookCode;
    private BorrowTicketStatus borrowTicketStatus;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
}
