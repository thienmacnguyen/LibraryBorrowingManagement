# SQL

## SQL-01: Các loại Constraint

### PRIMARY KEY

Đảm bảo mỗi bản ghi có một định danh duy nhất và không được `NULL`.

```sql
PRIMARY KEY
```

Giúp tránh trùng định danh giữa các bản ghi.

### FOREIGN KEY

Đảm bảo giá trị ở bảng hiện tại tham chiếu đến dữ liệu tồn tại ở bảng liên quan.

Giúp tránh dữ liệu tham chiếu không hợp lệ và dữ liệu mồ côi.

### UNIQUE

Đảm bảo giá trị trong một hoặc nhiều cột không bị trùng nhau.

Ví dụ:

```sql
email VARCHAR(255) UNIQUE
```

### NOT NULL

Không cho phép cột có giá trị `NULL`.

Dùng cho những dữ liệu bắt buộc phải có.

### CHECK

Đảm bảo dữ liệu thỏa mãn một điều kiện.

Ví dụ:

```sql
salary NUMERIC CHECK (salary > 0)
```

---

## SQL-02: INNER JOIN và LEFT JOIN

### INNER JOIN

Chỉ lấy những bản ghi có dữ liệu khớp ở cả hai bảng.

```sql
SELECT *
FROM books b
INNER JOIN borrow_records br
    ON b.id = br.book_id;
```

### LEFT JOIN

Lấy tất cả bản ghi của bảng bên trái.

Nếu không có bản ghi tương ứng ở bảng bên phải thì các cột của bảng bên phải sẽ có giá trị `NULL`.

Ví dụ, muốn lấy **tất cả sách kể cả những sách chưa từng được mượn**:

```sql
SELECT b.*, br.*
FROM books b
LEFT JOIN borrow_records br
    ON b.id = br.book_id;
```

---

## SQL-03: Transaction

Transaction là một nhóm các thao tác SQL được thực hiện như một đơn vị thống nhất.

Các thao tác trong transaction phải đảm bảo tính nhất quán của dữ liệu.

Ví dụ:

```sql
BEGIN;

UPDATE books
SET available_copies = available_copies - 1
WHERE id = 1;

INSERT INTO borrow_records(book_id, user_id)
VALUES (1, 10);

COMMIT;
```

Nếu một thao tác thất bại, có thể rollback:

```sql
ROLLBACK;
```

Nếu chỉ giảm số lượng sách nhưng việc tạo bản ghi mượn thất bại, dữ liệu sẽ không nhất quán.

Transaction giúp đảm bảo hai thao tác trên được xử lý cùng nhau: **hoặc cùng thành công, hoặc cùng được rollback**.

---

## SQL-04: Race Condition khi nhiều người cùng mượn sách

Giả sử chỉ còn:

```text
available_copies = 1
```

Hai người dùng cùng lúc gửi request mượn sách.

Nếu cả hai request đều đọc được:

```text
available_copies = 1
```

thì cả hai có thể cùng cho rằng sách vẫn còn và cùng thực hiện thao tác mượn.

Đây là **race condition**.

Transaction giúp nhóm các thao tác lại, nhưng chỉ dùng transaction đơn thuần chưa chắc đã giải quyết hoàn toàn race condition.

Với nghiệp vụ mượn sách, có thể cần kết hợp transaction với cơ chế khóa hoặc câu lệnh cập nhật có điều kiện.

Ví dụ:

```sql
BEGIN;

UPDATE books
SET available_copies = available_copies - 1
WHERE id = 1
  AND available_copies > 0;

-- Kiểm tra số row được update
-- Nếu = 1: tiếp tục tạo borrow record
-- Nếu = 0: sách đã hết

COMMIT;
```

Cách này đảm bảo chỉ request nào thực sự giảm được `available_copies` mới được phép tạo bản ghi mượn.
