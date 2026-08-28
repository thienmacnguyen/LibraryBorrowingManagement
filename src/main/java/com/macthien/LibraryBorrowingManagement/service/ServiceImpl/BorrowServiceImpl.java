package com.macthien.LibraryBorrowingManagement.service.ServiceImpl;

import com.macthien.LibraryBorrowingManagement.dto.BorrowTicketRequest;
import com.macthien.LibraryBorrowingManagement.dto.BorrowTicketResponse;
import com.macthien.LibraryBorrowingManagement.entity.Book;
import com.macthien.LibraryBorrowingManagement.entity.BorrowTicket;
import com.macthien.LibraryBorrowingManagement.entity.Reader;
import com.macthien.LibraryBorrowingManagement.enums.BorrowTicketStatus;
import com.macthien.LibraryBorrowingManagement.exception.ConflictException;
import com.macthien.LibraryBorrowingManagement.exception.NotFoundException;
import com.macthien.LibraryBorrowingManagement.service.BookService;
import com.macthien.LibraryBorrowingManagement.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BookService bookService;

    private final Map<Long, BorrowTicket> ticketStorage = new ConcurrentHashMap<>();
    private final Map<String, Reader> readerStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    @Override
    public synchronized BorrowTicketResponse borrowBook(BorrowTicketRequest request) {
        //Kiểm tra Reader tồn tại
        if (!readerStorage.containsKey(request.getReaderCode())) {
            throw new NotFoundException("Không tìm thấy độc giả với mã: " + request.getReaderCode());
        }

        //Kiểm tra Book tồn tại
        Book book = bookService.findEntityByCode(request.getBookCode());

        //Kiểm tra active
        if (!book.isActive()) {
            throw new ConflictException("Sách này hiện đã ngừng phục vụ (inactive)");
        }

        //Kiểm tra availableCopies > 0
        if (book.getAvailableCopies() <= 0) {
            throw new ConflictException("Sách đã hết bản sao có thể mượn (availableCopies = 0)");
        }

        //Giảm availableCopies đi 1
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        //Tạo phiếu BORROWED
        Long ticketId = idGenerator.getAndIncrement();
        BorrowTicket ticket = new BorrowTicket(
                ticketId,
                request.getReaderCode(),
                request.getBookCode(),
                BorrowTicketStatus.BORROWED,
                LocalDateTime.now(),
                null
        );
        ticketStorage.put(ticketId, ticket);

        return mapToResponse(ticket);
    }

    private BorrowTicketResponse mapToResponse(BorrowTicket ticket) {
        return BorrowTicketResponse.builder()
                .borrowTicketId(ticket.getBorrowTicketId())
                .readerCode(ticket.getReaderCode())
                .bookCode(ticket.getBookCode())
                .borrowTicketStatus(ticket.getBorrowTicketStatus())
                .borrowedAt(ticket.getBorrowedAt())
                .returnedAt(ticket.getReturnedAt())
                .build();
    }

    @Override
    public synchronized BorrowTicketResponse returnBook(Long ticketId) {
        BorrowTicket ticket = ticketStorage.get(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Không tìm thấy phiếu mượn với ID: " + ticketId);
        }

        //Không được trả cùng một phiếu 2 lần
        if (ticket.getBorrowTicketStatus() == BorrowTicketStatus.RETURNED) {
            throw new ConflictException("Phiếu mượn #" + ticketId + " này đã được trả trước đó");
        }

        //Đổi trạng thái sang RETURNED và gán returnedAt
        ticket.setBorrowTicketStatus(BorrowTicketStatus.RETURNED);
        ticket.setReturnedAt(LocalDateTime.now());

        //Tăng availableCopies lên 1
        Book book = bookService.findEntityByCode(ticket.getBookCode());
        if (book.getAvailableCopies() >= book.getTotalCopies()) {
            throw new ConflictException("Số lượng khả dụng không thể vượt quá tổng số bản sách");
        }
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return mapToResponse(ticket);
    }

    @Override
    public BorrowTicketResponse getTicketById(Long ticketId) {
        BorrowTicket ticket = ticketStorage.get(ticketId);
        if (ticket == null) {
            throw new NotFoundException("Không tìm thấy phiếu mượn với ID: " + ticketId);
        }
        return mapToResponse(ticket);
    }
}
