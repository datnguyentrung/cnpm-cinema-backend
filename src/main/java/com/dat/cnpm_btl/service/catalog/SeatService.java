package com.dat.cnpm_btl.service.catalog;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import com.dat.cnpm_btl.repository.catalog.SeatRepository;
import com.dat.cnpm_btl.util.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public SeatDTO.SeatResponse getSeatById(Integer seatId) {
        log.info("Fetching seat for seatId: {}", seatId);
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + seatId));
        return seatMapper.toSeatResponse(seat);
    }

    public List<SeatDTO.SeatResponse> getSeatsByIds(List<Integer> seatIds) {
        log.info("Fetching seats for seatIds: {}", seatIds);

        // 1. Validate Input: Tránh trường hợp truyền list rỗng xuống DB
        if (seatIds == null || seatIds.isEmpty()) {
            return List.of();
        }

        // 2. Loại bỏ ID trùng lặp (tránh trường hợp user spam click chọn 1 ghế 2 lần)
        Set<Integer> uniqueRequestedIds = new HashSet<>(seatIds);

        // 3. Query DB với danh sách ID đã deduplicate
        List<Seat> seats = seatRepository.findBySeatIdIn(new ArrayList<>(uniqueRequestedIds));

        // 4. KIỂM TRA LOGIC NGHIỆP VỤ (Vô cùng quan trọng)
        // Nếu số ghế tìm thấy trong DB ít hơn số ID yêu cầu -> Có ghế ma!
        if (seats.size() != uniqueRequestedIds.size()) {

            // Tìm xem chính xác là ID nào không tồn tại để báo lỗi cho chuẩn
            Set<Integer> foundIds = seats.stream()
                    .map(Seat::getSeatId)
                    .collect(Collectors.toSet());

            Set<Integer> missingIds = new HashSet<>(uniqueRequestedIds);
            missingIds.removeAll(foundIds); // Lọc ra những ID bị thiếu

            log.error("Failed to fetch seats. Missing IDs: {}", missingIds);
            throw new ResourceNotFoundException("Không tìm thấy các ghế có ID: " + missingIds);
        }

        // 5. Mapping và trả về
        return seatMapper.toSeatResponseList(seats);
    }

    public List<SeatDTO.SeatResponse> getSeatsByRoomId(Integer roomId) {
        log.info("Fetching seats for roomId: {}", roomId);
        List<Seat> seats = seatRepository.findByRoomIdAndIsActive(roomId, true);
        return seatMapper.toSeatResponseList(seats);
    }

    public List<SeatDTO.SeatResponse> getSeatsByRoomIdAndSeatIds(Integer roomId, List<Integer> seatIds) {
        log.info("Fetching seats for seatIds: {}", seatIds);
        List<Seat> seats = seatRepository.findByRoomIdAndSeatIdInAndIsActive(roomId, seatIds, true);
        return seatMapper.toSeatResponseList(seats);
    }
}
