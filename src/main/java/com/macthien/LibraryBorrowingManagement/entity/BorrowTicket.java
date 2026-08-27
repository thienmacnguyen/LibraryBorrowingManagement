package com.macthien.LibraryBorrowingManagement.entity;

import com.macthien.LibraryBorrowingManagement.enums.BorrowTicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "BorrowTicket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BorrowTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowTicket_id")
    private Long borrowTicketId;

    private String readerCode;

    private String bookCode;

    private BorrowTicketStatus borrowTicketStatus;

    private LocalDateTime borrowedAt;

    private LocalDateTime returnedAt;
}
