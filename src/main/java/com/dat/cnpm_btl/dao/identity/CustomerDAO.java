package com.dat.cnpm_btl.dao.identity;

import com.dat.cnpm_btl.domain.identity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, UUID> {
}
