package com.dat.cnpm_btl.repository.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
