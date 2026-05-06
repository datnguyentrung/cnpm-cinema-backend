package com.dat.cnpm_btl.dao.ticketing;

import com.dat.cnpm_btl.domain.ticketing.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemDAO extends JpaRepository<InvoiceItem, Long> {
}
