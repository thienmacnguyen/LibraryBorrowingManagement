CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_total_copies CHECK (total_copies > 0),
    CONSTRAINT chk_available_copies CHECK (available_copies >= 0 AND available_copies <= total_copies)
);

CREATE TABLE readers (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE borrow_tickets (
    id SERIAL PRIMARY KEY,
    reader_id INT NOT NULL,
    book_id INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    borrowed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    returned_at TIMESTAMP,
    CONSTRAINT fk_ticket_reader FOREIGN KEY (reader_id) REFERENCES readers(id),
    CONSTRAINT fk_ticket_book FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT chk_ticket_status CHECK (status IN ('BORROWED', 'RETURNED'))
);