package com.dat.cnpm_btl.service.catalog;

import com.dat.cnpm_btl.domain.catalog.Seat;
import com.dat.cnpm_btl.dto.catalog.SeatDTO;
import com.dat.cnpm_btl.mapper.catalog.SeatMapper;
import com.dat.cnpm_btl.repository.catalog.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    public List<SeatDTO.SeatResponse> getSeatsByRoomId(Integer roomId) {
        log.info("Fetching seats for roomId: {}", roomId);
        List<Seat> seats = seatRepository.findByRoomIdAndIsActive(roomId, true);
        return seatMapper.toSeatResponseList(seats);
    }
}
