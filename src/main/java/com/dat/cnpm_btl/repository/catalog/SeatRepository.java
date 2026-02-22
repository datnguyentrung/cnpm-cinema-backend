package com.dat.cnpm_btl.repository.catalog;

import com.dat.cnpm_btl.domain.catalog.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByRoomIdAndIsActive(Integer roomId, Boolean isActive);

    List<Seat> findByRoomIdAndSeatIdInAndIsActive(Integer roomId, List<Integer> seatIds, boolean b);
}
