package com.macthien.LibraryBorrowingManagement.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BorrowTicketRequest {
    @NotBlank
    private String readerCode;

    @NotBlank
    private String bookCode;
}
