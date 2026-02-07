# DOMAIN ENTITIES - HOÀN THIỆN

## ✅ ĐÃ HOÀN THÀNH

### Schema CATALOG (8 entities)
1. ✅ **Cinema** - Rạp chiếu phim
2. ✅ **Room** - Phòng chiếu
3. ✅ **Seat** - Ghế ngồi
4. ✅ **Movie** - Phim
5. ✅ **Director** - Đạo diễn
6. ✅ **MovieDirector** - Quan hệ nhiều-nhiều giữa Movie và Director
7. ✅ **Genre** - Thể loại phim
8. ✅ **MovieGenre** - Quan hệ nhiều-nhiều giữa Movie và Genre

### Schema IDENTITY (5 entities)
1. ✅ **User** - Người dùng (class cha)
2. ✅ **Customer** - Khách hàng (kế thừa User)
3. ✅ **Employee** - Nhân viên (kế thừa User)
4. ✅ **Role** - Vai trò
5. ✅ **AuthToken** - Token xác thực

### Schema TICKETING (6 entities)
1. ✅ **ShowTime** - Suất chiếu
2. ✅ **Booking** - Đơn đặt vé
3. ✅ **Ticket** - Vé xem phim
4. ✅ **Product** - Sản phẩm (bắp, nước)
5. ✅ **Invoice** - Hóa đơn
6. ✅ **InvoiceItem** - Chi tiết hóa đơn

## 📊 TỔNG KẾT
- **Tổng số entities**: 19 entities
- **Compilation status**: ✅ BUILD SUCCESS
- **JPA Annotations**: Đầy đủ (@Entity, @Table, @Column, @ManyToOne, @Id, v.v.)
- **Lombok**: Sử dụng @Getter, @Setter, @Builder, @NoArgsConstructor, @AllArgsConstructor
- **Indexes**: Đã thêm indexes cho các trường quan trọng
- **Unique Constraints**: Đã thêm unique constraints theo schema
- **Composite Keys**: Đã implement cho MovieDirector và MovieGenre

## 🔧 CẢI TIẾN ĐÃ THỰC HIỆN

### Enums đã cập nhật
1. ✅ **BookingStatus**: Đổi từ {PENDING, SUCCESS, FAILED} → {PENDING, CONFIRMED, CANCELLED, REFUNDED}
2. ✅ **SeatType**: Đổi NORMAL → STANDARD
3. ✅ **PaymentMethod**: Đổi CREDIT_CARD → CARD

## 📝 CHI TIẾT CÁC ENTITY

### CATALOG SCHEMA

#### 1. Cinema
- UUID primary key (cinema_id)
- Quan hệ với Employee (manager)
- Status: OPEN, CLOSED, MAINTENANCE
- Indexes: manager_id, status

#### 2. Room
- Integer primary key (room_id - Auto increment)
- Foreign key đến Cinema
- Unique constraint: (cinema_id, name)
- Chứa thông tin: total_rows, total_cols, type (2D, 3D, IMAX)

#### 3. Seat
- Integer primary key (seat_id - Auto increment)
- Foreign key đến Room
- Unique constraints: (room_id, row_label, seat_number), (room_id, grid_row, grid_col)
- Type: STANDARD, VIP, COUPLE
- is_active: để disable ghế hỏng

#### 4. Movie
- UUID primary key (movie_id)
- Thông tin: title, duration_minutes, poster_url, description
- release_date, age_rating (P, C13, C16, C18)
- Indexes: release_date, age_rating

#### 5. Director
- Integer primary key (director_id - Auto increment)
- Thông tin: full_name, birth_date, nationality
- Index: nationality

#### 6. MovieDirector
- Composite key: (movie_id, director_id)
- Many-to-many relationship
- Indexes: movie_id, director_id

#### 7. Genre
- Integer primary key (genre_id - Auto increment)
- name: unique

#### 8. MovieGenre
- Composite key: (movie_id, genre_id)
- Many-to-many relationship
- Indexes: movie_id, genre_id

### TICKETING SCHEMA

#### 1. ShowTime
- UUID primary key (showtime_id)
- Foreign keys: movie_id, room_id
- Unique constraint: (room_id, start_time)
- base_price: Giá vé cơ bản
- Status: OPENING, SOLD_OUT, CANCELLED
- Indexes: movie_id, room_id, start_time, status

#### 2. Booking
- UUID primary key (booking_id)
- Foreign keys: user_id, showtime_id
- created_at (auditing), expired_at
- Status: PENDING, CONFIRMED, CANCELLED, REFUNDED
- Indexes: user_id, showtime_id, status, created_at

#### 3. Ticket
- UUID primary key (ticket_id)
- Foreign keys: booking_id, showtime_id, seat_id
- ticket_code: unique (để tạo QR)
- price: Giá thực tế tại thời điểm mua
- Status: HOLD, PAID, USED, CANCELLED
- check_in_time: Khi quét QR
- Indexes: booking_id, showtime_id, seat_id, ticket_code, status

#### 4. Product
- Integer primary key (product_id - Auto increment)
- Type: FOOD, DRINK, COMBO
- price, description
- is_active: Còn bán hay không
- Indexes: type, is_active

#### 5. Invoice
- UUID primary key (invoice_id)
- Foreign keys: customer_id, employee_id (nullable), booking_id
- total_amount, final_amount (sau khuyến mãi)
- payment_method: CASH, MOMO, VNPAY, BANK_TRANSFER, CARD
- Status: PENDING, PAID, REFUNDED
- Indexes: customer_id, employee_id, booking_id, status, created_at

#### 6. InvoiceItem
- BIGINT primary key (id - Auto increment)
- Foreign keys: invoice_id, product_id
- Unique constraint: (invoice_id, product_id)
- quantity, unit_price (lưu cứng giá tại thời điểm mua)
- Indexes: invoice_id, product_id

## 🎯 FEATURES NỔI BẬT

### 1. Inheritance Strategy
- User → Customer, Employee sử dụng JOINED strategy
- SuperBuilder cho kế thừa

### 2. Auditing
- Sử dụng @CreatedDate, @LastModifiedDate
- EntityListeners(AuditingEntityListener.class)

### 3. UUID vs Auto Increment
- UUID: Cinema, Movie, ShowTime, Booking, Ticket, Invoice (dữ liệu distributed)
- Auto Increment: Room, Seat, Director, Genre, Product, InvoiceItem (dữ liệu local)

### 4. Lazy Loading
- Tất cả relationships đều sử dụng FetchType.LAZY để tối ưu performance

### 5. Composite Keys
- MovieDirector, MovieGenre sử dụng @IdClass pattern
- Implement Serializable cho composite key classes

## 🔍 VALIDATION STATUS
```
[INFO] BUILD SUCCESS
[INFO] Compiling 31 source files with javac [debug parameters release 21] to target\classes
```

## 📂 CẤU TRÚC FOLDER
```
domain/
├── catalog/
│   ├── Cinema.java
│   ├── Director.java
│   ├── Genre.java
│   ├── Movie.java
│   ├── MovieDirector.java
│   ├── MovieGenre.java
│   ├── Room.java
│   └── Seat.java
├── identity/
│   ├── AuthToken.java
│   ├── Customer.java
│   ├── Employee.java
│   ├── Role.java
│   └── User.java
└── ticketing/
    ├── Booking.java
    ├── Invoice.java
    ├── InvoiceItem.java
    ├── Product.java
    ├── ShowTime.java
    └── Ticket.java
```

## 🚀 BƯỚC TIẾP THEO

1. **Tạo Flyway Migrations**: Tạo các file SQL migration để tạo database schema
2. **Tạo Repositories**: Implement JPA repositories cho các entities
3. **Tạo DTOs**: Tạo Data Transfer Objects cho API
4. **Tạo Mappers**: Implement MapStruct mappers
5. **Tạo Services**: Business logic layer
6. **Tạo Controllers**: REST API endpoints

---
**Ngày hoàn thành**: 07/02/2026
**Trạng thái**: ✅ HOÀN TẤT 100%
