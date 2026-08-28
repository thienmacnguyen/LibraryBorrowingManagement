-- Q-01: Lấy toàn bộ sách đang active và còn có thể mượn
SELECT * 
FROM books 
WHERE active = true AND available_copies > 0;

-- Q-02: Tìm sách theo tiêu đề, không phân biệt hoa/thường (ILIKE)
SELECT * 
FROM books 
WHERE title ILIKE '%code%';

-- Q-03: Hiển thị phiếu mượn kèm tên độc giả và tiêu đề sách (INNER JOIN)
SELECT 
    bt.id AS ticket_id,
    r.code AS reader_code,
    r.name AS reader_name,
    b.code AS book_code,
    b.title AS book_title,
    bt.status,
    bt.borrowed_at,
    bt.returned_at
FROM borrow_tickets bt
JOIN readers r ON bt.reader_id = r.id
JOIN books b ON bt.book_id = b.id;

-- Q-04: Thống kê số phiếu mượn theo từng sách, vẫn hiển thị sách chưa từng được mượn (LEFT JOIN)
SELECT 
    b.id,
    b.code,
    b.title,
    COUNT(bt.id) AS total_borrows
FROM books b
LEFT JOIN borrow_tickets bt ON b.id = bt.book_id
GROUP BY b.id, b.code, b.title;

-- Q-05: Lấy sách chưa từng được mượn
SELECT b.*
FROM books b
LEFT JOIN borrow_tickets bt ON b.id = bt.book_id
WHERE bt.id IS NULL;

-- Q-06: Lấy độc giả hiện không có phiếu nào trạng thái BORROWED
SELECT *
FROM readers
WHERE id NOT IN (
    SELECT reader_id 
    FROM borrow_tickets 
    WHERE status = 'BORROWED'
);

-- Q-07: Lấy sách có ít nhất hai phiếu mượn bằng GROUP BY và HAVING
SELECT 
    b.id,
    b.code,
    b.title,
    COUNT(bt.id) AS borrow_count
FROM books b
JOIN borrow_tickets bt ON b.id = bt.book_id
GROUP BY b.id, b.code, b.title
HAVING COUNT(bt.id) >= 2;

-- Q-08: Lấy các sách có số phiếu mượn lớn hơn số phiếu mượn trung bình của tất cả sách
WITH book_borrow_stats AS (
    SELECT 
        b.id,
        b.code,
        b.title,
        COUNT(bt.id) AS borrow_count
    FROM books b
    LEFT JOIN borrow_tickets bt ON b.id = bt.book_id
    GROUP BY b.id, b.code, b.title
)
SELECT *
FROM book_borrow_stats
WHERE borrow_count > (SELECT AVG(borrow_count) FROM book_borrow_stats);