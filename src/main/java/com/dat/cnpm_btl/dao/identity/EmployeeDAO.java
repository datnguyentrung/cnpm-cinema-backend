package com.dat.cnpm_btl.dao.identity;

import com.dat.cnpm_btl.domain.identity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, UUID> {
}
