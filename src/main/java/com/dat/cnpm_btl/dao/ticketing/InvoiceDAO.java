package com.dat.cnpm_btl.dao.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceDAO extends JpaRepository<Invoice, UUID> {
}
