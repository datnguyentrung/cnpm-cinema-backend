package com.dat.cnpm_btl.repository.ticketing;

import com.dat.cnpm_btl.domain.ticketing.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
