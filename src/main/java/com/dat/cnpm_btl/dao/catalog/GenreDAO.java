package com.dat.cnpm_btl.dao.catalog;

import com.dat.cnpm_btl.domain.catalog.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreDAO extends JpaRepository<Genre, Integer> {
}
