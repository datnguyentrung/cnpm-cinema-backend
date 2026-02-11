package com.dat.cnpm_btl.repository.ticketing;

import com.dat.cnpm_btl.domain.ticketing.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
