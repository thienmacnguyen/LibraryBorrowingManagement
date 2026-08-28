package com.macthien.LibraryBorrowingManagement.service;

import com.macthien.LibraryBorrowingManagement.dto.BorrowTicketRequest;
import com.macthien.LibraryBorrowingManagement.dto.BorrowTicketResponse;

public interface BorrowService {
    BorrowTicketResponse borrowBook(BorrowTicketRequest request);
    BorrowTicketResponse returnBook(Long ticketId);
    BorrowTicketResponse getTicketById(Long ticketId);
}
