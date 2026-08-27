# Java Core

## JC-01: Class, Object, Constructor, Encapsulation

* **Class** là khuôn mẫu định nghĩa object có những dữ liệu gì và có thể thực hiện những hành vi gì.
* **Object** là một thực thể được tạo ra từ class.
* **Constructor** là hàm khởi tạo object, được gọi khi sử dụng `new` để tạo object. Constructor dùng để thiết lập dữ liệu ban đầu cho object.
* **Encapsulation (đóng gói)** là việc che giấu dữ liệu bên trong object và chỉ cho phép truy cập hoặc thay đổi dữ liệu thông qua các phương thức được kiểm soát.

---

## JC-02: Tại sao không cho phép bên ngoài gán `availableCopies`?

Không nên cho phép bên ngoài tùy ý gán `availableCopies` vì đây là dữ liệu liên quan trực tiếp đến nghiệp vụ mượn và trả sách.

Ví dụ không thể cho phép:

```java
book.setAvailableCopies(-5);
```

Vì số lượng sách có thể mượn không thể là số âm.

Thay vào đó, dữ liệu nên được thay đổi thông qua các method nghiệp vụ như:

```java
book.borrow();
book.returnBook();
```

Như vậy object có thể tự đảm bảo dữ liệu luôn hợp lệ.

---

## JC-03: ArrayList và HashMap

### ArrayList

* Lưu dữ liệu theo thứ tự.
* Cho phép phần tử trùng nhau.
* Tìm kiếm theo ID thường phải duyệt từng phần tử.

### HashMap

* Lưu dữ liệu theo dạng `key-value`.
* `key` không được trùng nhau.
* Cho phép tìm kiếm trực tiếp thông qua `key`.

Ví dụ:

```java
Map<Long, Book> books = new HashMap<>();
```

Có thể tìm sách bằng ID:

```java
Book book = books.get(id);
```

Nếu nghiệp vụ thường xuyên tìm sách theo `id`, `HashMap<Long, Book>` phù hợp hơn vì việc tìm kiếm theo key có hiệu quả trung bình `O(1)`, trong khi tìm kiếm tuần tự trong `ArrayList` là `O(n)`.

---

## JC-04: `==` và `equals()`

* `==` với object kiểm tra xem hai biến có đang tham chiếu đến cùng một object hay không.
* `equals()` dùng để kiểm tra hai object có bằng nhau theo logic được định nghĩa hay không.

Với `String`, nên dùng:

```java
name.equals(otherName);
```

hoặc an toàn hơn khi biến có thể `null`:

```java
Objects.equals(name, otherName);
```

Không nên dùng:

```java
name == otherName;
```

để kiểm tra nội dung của String.

---

## JC-05: Interface và Abstract Class

### Interface

Interface định nghĩa một **contract** mà class triển khai phải tuân theo.

Ví dụ:

```java
public interface BookService {
    Book findBook(Long id);
}
```

Class có thể `implements` nhiều interface.

### Abstract Class

Abstract class có thể chứa cả thuộc tính, method thông thường và abstract method.

Class con sử dụng `extends` để kế thừa abstract class.

Trong bài có thể sử dụng:

```java
public interface BookService {
    Book findBook(Long id);
}
```

Sau đó:

```java
public class BookServiceImpl implements BookService {
}
```

Việc này giúp các lớp khác phụ thuộc vào abstraction (`BookService`) thay vì phụ thuộc trực tiếp vào implementation.

---

## JC-06: Overloading và Overriding

### Overloading

Các method có cùng tên nhưng khác danh sách tham số:

* Khác số lượng tham số.
* Khác kiểu dữ liệu tham số.
* Khác thứ tự kiểu dữ liệu tham số.

Ví dụ:

```java
public Book findBook(Long id) {
    return null;
}

public Book findBook(String title) {
    return null;
}
```

### Overriding

Xảy ra khi class con định nghĩa lại method của class cha hoặc implementation của interface.

Ví dụ:

```java
public class Animal {
    public void sound() {
        System.out.println("Animal sound");
    }
}

public class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("Dog sound");
    }
}
```

---

## JC-07: `throw` và `throws`

* `throw` dùng để **chủ động ném ra một exception**.
* `throws` dùng để khai báo rằng method **có thể phát sinh exception** và exception đó được xử lý ở nơi gọi method.

Ví dụ:

```java
throw new IllegalArgumentException("Invalid price");
```

và:

```java
public void readFile() throws IOException {
}
```

---

## JC-08: Tại sao không nên trả về `null`?

Không nên trả `null` một cách tùy tiện vì nó có thể khiến `null` được truyền sang các tầng khác.

Khi code phía ngoài sử dụng object mà không kiểm tra `null`, có thể xảy ra:

```java
NullPointerException
```

Ví dụ, thay vì trả `null`, có thể sử dụng `Optional` cho trường hợp dữ liệu có thể không tồn tại:

```java
Optional<Book> findById(Long id);
```

---

## JC-09: Luồng dữ liệu trong ứng dụng

Luồng xử lý cơ bản:

```text
Client
   ↓ HTTP Request
Controller
   ↓ Request DTO
Service
   ↓ Entity
Repository
   ↓
Database
   ↓
Repository
   ↓ Entity
Service
   ↓ Response DTO
Controller
   ↓ HTTP Response
Client
```

DTO được dùng để giao tiếp giữa API và client, còn Entity đại diện cho dữ liệu được xử lý trong tầng nghiệp vụ và persistence.

---

## JC-10: Các annotation trong Spring

### `@RequestBody`

Lấy dữ liệu JSON từ HTTP Request Body và chuyển thành Java object, thường là DTO.

```java
@PostMapping
public Book create(@RequestBody BookRequest request) {
}
```

### `@PathVariable`

Lấy dữ liệu từ URL path.

```java
@GetMapping("/{id}")
public Book find(@PathVariable Long id) {
}
```

Ví dụ:

```text
GET /books/10
```

thì `id = 10`.

### `@RequestParam`

Lấy dữ liệu từ query parameter.

```java
@GetMapping
public List<Book> search(@RequestParam String title) {
}
```

Ví dụ:

```text
GET /books?title=Java
```

### `@Valid`

Yêu cầu Spring thực hiện validation DTO dựa trên các annotation validation.

```java
@PostMapping
public Book create(@Valid @RequestBody BookRequest request) {
}
```

---

## JC-11: HTTP Status 201 và 409

### `201 Created`

Dùng khi request tạo mới resource thành công.

Ví dụ:

```text
POST /books
→ 201 Created
```

Điều này cho biết resource mới đã được tạo thành công.

### `409 Conflict`

Dùng khi request xung đột với trạng thái hiện tại của hệ thống.

Ví dụ:

Một cuốn sách có:

```text
availableCopies = 0
```

nhưng client vẫn gửi request mượn sách.

Request hợp lệ về mặt HTTP nhưng không thể thực hiện theo trạng thái nghiệp vụ hiện tại, vì vậy có thể trả:

```text
409 Conflict
```

`409` không đơn thuần có nghĩa là "server bị lỗi", mà thể hiện request xung đột với trạng thái hiện tại của resource hoặc hệ thống.
