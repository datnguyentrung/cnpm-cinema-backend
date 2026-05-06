package com.dat.cnpm_btl.dao.catalog;

import com.dat.cnpm_btl.domain.catalog.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CinemaDAO extends JpaRepository<Cinema, UUID> {
}
