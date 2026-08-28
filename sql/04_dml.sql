-- DML-01: Thêm sách và độc giả
INSERT INTO books (code, title, total_copies, available_copies, active)
VALUES ('B006', 'Database Internals', 3, 3, true);

INSERT INTO readers (code, name)
VALUES ('R004', 'Hoang Nam');


-- DML-02 & DML-04: Mượn sách
BEGIN;

-- Kiểm tra và khóa sách để tránh race condition
SELECT id, available_copies, active
FROM books
WHERE code = 'B006'
FOR UPDATE;

-- Giảm số lượng sách khả dụng
UPDATE books
SET available_copies = available_copies - 1
WHERE code = 'B006'
  AND available_copies > 0
  AND active = true;

-- Tạo phiếu mượn
INSERT INTO borrow_tickets (reader_id, book_id, status, borrowed_at)
VALUES (
    (SELECT id FROM readers WHERE code = 'R004'),
    (SELECT id FROM books WHERE code = 'B006'),
    'BORROWED',
    CURRENT_TIMESTAMP
);

COMMIT;


-- DML-03 & DML-04: Trả sách
BEGIN;

-- Kiểm tra và khóa phiếu mượn
SELECT id, book_id, status
FROM borrow_tickets
WHERE id = 1
FOR UPDATE;

-- Cập nhật trạng thái trả sách
UPDATE borrow_tickets
SET status = 'RETURNED',
    returned_at = CURRENT_TIMESTAMP
WHERE id = 1
  AND status = 'BORROWED';

-- Tăng số lượng sách khả dụng
UPDATE books
SET available_copies = available_copies + 1
WHERE id = (
    SELECT book_id
    FROM borrow_tickets
    WHERE id = 1
);

COMMIT;