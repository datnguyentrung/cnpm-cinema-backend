package com.dat.cnpm_btl.service.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Booking;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.dto.ticketing.ShowtimeDTO;
import com.dat.cnpm_btl.dto.ticketing.TicketDTO;
import com.dat.cnpm_btl.enums.ticketing.BookingStatus;
import com.dat.cnpm_btl.enums.ticketing.TicketStatus;
import com.dat.cnpm_btl.repository.ticketing.BookingRepository;
import com.dat.cnpm_btl.service.catalog.SeatService;
import com.dat.cnpm_btl.util.SeatValidationUtil;
import com.dat.cnpm_btl.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository bookingRepository;

    private final ShowtimeService showtimeService;

    private final TicketService ticketService;

    private final SeatService seatService;

    @Transactional
    public List<TicketDTO.TicketResponse> bookSeats(String showtimeId, List<Integer> seatIds){
        ShowtimeDTO.ShowTimeWithSeatsResponse showtime = showtimeService.getShowtimeWithSeats(showtimeId);

        // 1. Chuyển List thành Set (BẮT BUỘC để tối ưu O(1) khi check contains)
        Set<Integer> bookedSeatIds = showtime.getSelectedSeats().stream()
                .map(SeatDTO.SeatResponse::getSeatId)
                .collect(Collectors.toSet());

        // 2. Chặn sớm: Kiểm tra ghế đã bị đặt chưa
        for (Integer seatId : seatIds) {
            if (bookedSeatIds.contains(seatId)) {
                log.warn("Attempt to book unavailable seat: ID {} for showtime {}", seatId, showtimeId);
                throw new IllegalArgumentException("Ghế có ID " + seatId + " đã có người đặt!");
            }
        }

        // 3. Kéo dữ liệu ghế
        Integer roomId = showtime.getRoomResponse().getRoomId();
        List<SeatDTO.SeatResponse> allRoomSeats = seatService.getSeatsByRoomId(roomId);
        List<SeatDTO.SeatResponse> selectedSeats = seatService.getSeatsByRoomIdAndSeatIds(roomId, seatIds);

        if (selectedSeats.size() != seatIds.size()) {
            throw new IllegalArgumentException("Một hoặc nhiều ghế không tồn tại trong phòng chiếu này.");
        }

        // 4. Validate nghiệp vụ cốt lõi (Hard Block Orphan Seat)
        // Nếu vi phạm, hàm này sẽ tự động throw IllegalArgumentException và block giao dịch
        SeatValidationUtil.validateSeatRules(allRoomSeats, selectedSeats, bookedSeatIds);

        // 5. Mọi thứ OK -> Tiến hành tạo Booking và Tickets

//        // 5.1. Lấy userId từ SecurityContext (người dùng đang đăng nhập)
//        String currentUserId = SecurityUtil.getCurrentUserLogin()
//                .orElseThrow(() -> new IllegalStateException("User not authenticated"));

        String currentUserId = "c0000000-0000-0000-0000-000000000001"; // TODO: Thay bằng userId thực tế khi có auth
        
        // 5.2. Tạo Booking
        Booking booking = Booking.builder()
                .userId(UUID.fromString(currentUserId))
                .status(BookingStatus.CONFIRMED) // TODO: Có thể để PENDING nếu muốn thêm bước thanh toán sau này
                .expiredAt(Instant.now().plus(10, ChronoUnit.MINUTES)) // Giữ ghế 10 phút
                .build();

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Created booking with ID: {} for user: {}", savedBooking.getBookingId(), currentUserId);

        // 5.3. Tạo Tickets cho từng ghế
        TicketDTO.BulkCreateTicketRequest ticketRequest = new TicketDTO.BulkCreateTicketRequest();
        ticketRequest.setBookingId(savedBooking.getBookingId().toString());
        ticketRequest.setShowtimeId(showtimeId);
        ticketRequest.setSeatIds(seatIds);
        ticketRequest.setPrice(showtime.getTicketPrice()); // Giá từ suất chiếu
        ticketRequest.setStatus(TicketStatus.PAID); // TODO: Chuyển sang HOLD nếu muốn thêm bước thanh toán sau này

        List<TicketDTO.TicketResponse> tickets = ticketService.createTicketsBulk(ticketRequest);

        log.info("Successfully created booking {} with {} tickets for showtime {}",
                savedBooking.getBookingId(), tickets.size(), showtimeId);

        return tickets;
    }
}
