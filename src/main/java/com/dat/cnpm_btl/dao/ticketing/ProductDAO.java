package com.dat.cnpm_btl.dao.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
}
