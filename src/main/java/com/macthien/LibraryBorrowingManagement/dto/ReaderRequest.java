package com.macthien.LibraryBorrowingManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReaderRequest {
    @NotBlank
    private String readerCode;

    private String readerName;
}
