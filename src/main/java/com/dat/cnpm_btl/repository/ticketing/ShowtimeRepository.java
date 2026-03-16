package com.dat.cnpm_btl.repository.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, String> {
    List<Showtime> findByStartTimeBetween(Instant startTimeAfter, Instant startTimeBefore);
}
