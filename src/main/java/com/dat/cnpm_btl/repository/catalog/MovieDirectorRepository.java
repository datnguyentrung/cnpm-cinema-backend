package com.dat.cnpm_btl.repository.catalog;

import com.dat.cnpm_btl.domain.catalog.MovieDirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDirectorRepository extends JpaRepository<MovieDirector, MovieDirector.MovieDirectorId> {
}
