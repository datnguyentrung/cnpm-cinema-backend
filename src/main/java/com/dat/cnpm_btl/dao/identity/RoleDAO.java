package com.dat.cnpm_btl.dao.identity;

import com.dat.cnpm_btl.domain.identity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends JpaRepository<Role, String> {
}
