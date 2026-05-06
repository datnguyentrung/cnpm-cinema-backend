package com.dat.cnpm_btl.dao.catalog;

import com.dat.cnpm_btl.domain.catalog.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatDAO extends JpaRepository<Seat, Integer> {
    List<Seat> findByRoomIdAndIsActive(Integer roomId, Boolean isActive);

    List<Seat> findByRoomIdAndIdInAndIsActive(Integer roomId, List<Integer> seatIds, boolean b);

    List<Seat> findByIdIn(List<Integer> seatIds);
}
