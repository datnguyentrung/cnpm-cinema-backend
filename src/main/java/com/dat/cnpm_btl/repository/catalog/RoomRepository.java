package com.dat.cnpm_btl.repository.catalog;

import com.dat.cnpm_btl.domain.catalog.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
