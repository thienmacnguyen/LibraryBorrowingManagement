INSERT INTO books (code, title, total_copies, available_copies, active) VALUES
('B001', 'Clean Code', 3, 2, true),
('B002', 'Design Patterns', 2, 1, true),
('B003', 'Refactoring', 2, 0, true),
('B004', 'Domain-Driven Design', 1, 1, true),
('B005', 'The Pragmatic Programmer', 2, 2, true);


INSERT INTO readers (code, name) VALUES
('R001', 'Linh Duc'),
('R002', 'Mac Thien'),
('R003', 'Van An');

INSERT INTO borrow_tickets (reader_id, book_id, status, borrowed_at, returned_at) VALUES
(1, 1, 'BORROWED', NOW() - INTERVAL '3 days', NULL),
(2, 2, 'BORROWED', NOW() - INTERVAL '2 days', NULL),
(1, 3, 'BORROWED', NOW() - INTERVAL '1 days', NULL),
(2, 3, 'BORROWED', NOW() - INTERVAL '1 days', NULL),
(1, 1, 'RETURNED', NOW() - INTERVAL '10 days', NOW() - INTERVAL '5 days'),
(3, 4, 'RETURNED', NOW() - INTERVAL '8 days', NOW() - INTERVAL '2 days');